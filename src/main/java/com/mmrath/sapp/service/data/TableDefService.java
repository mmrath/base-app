package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.ColumnDef;
import com.mmrath.sapp.domain.data.ColumnType;
import com.mmrath.sapp.domain.data.DataType;
import com.mmrath.sapp.domain.data.TableDef;
import com.mmrath.sapp.repository.data.ColumnDefRepository;
import com.mmrath.sapp.repository.data.TableDefRepository;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TableDefService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableDefService.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ColumnDefRepository columnDefRepository;

    @Autowired
    private TableDefRepository tableDefRepository;

    public void save(TableDef tableDef) {
        tableDefRepository.save(tableDef);
    }

    public void delete(TableDef tableDef) {
        tableDefRepository.delete(tableDef);
    }

    public List<ColumnDef> getColumnDefFromDb(final String tableName) {
        String tableNamePattern = "\\w+";
        if (tableName == null || !tableName.matches(tableNamePattern)) {
            return new ArrayList<>();
        }
        return jdbcTemplate.execute(new ConnectionCallback<List<ColumnDef>>() {
            @Override
            public List<ColumnDef> doInConnection(Connection connection) throws SQLException, DataAccessException {
                List<ColumnDef> columnDefs = new ArrayList<ColumnDef>();

                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet pkRs = metaData.getPrimaryKeys(null, null, tableName);
                List<String> pks = new ArrayList<String>();
                while (pkRs.next()) {
                    pks.add(pkRs.getString("COLUMN_NAME"));
                }

                ResultSet rs = metaData.getColumns(null, null, tableName, null);
                ColumnDefExtractor extractor = new ColumnDefExtractor(pks);
                while (rs.next()) {
                    columnDefs.add(extractor.extract(rs));
                }
                return columnDefs;
            }
        });
    }

    public static class ColumnDefExtractor {
        private final List<String> pkColumns;

        private final ArrayList<String> CREATED_BY_COLUMN_NAMES = new ArrayList<>();
        private final ArrayList<String> CREATED_DATE_COLUMN_NAMES = new ArrayList<>();
        private final ArrayList<String> MODIFIED_BY_COLUMN_NAMES = new ArrayList<>();
        private final ArrayList<String> MODIFIED_DATE_COLUMN_NAMES = new ArrayList<>();
        private final ArrayList<String> VERSION_COLUMN_NAMES = new ArrayList<>();

        public ColumnDefExtractor(List<String> pkColumns) {
            this.pkColumns = pkColumns;
            CREATED_BY_COLUMN_NAMES.add("CREATED_BY");

            CREATED_DATE_COLUMN_NAMES.add("CREATED_DATE");
            MODIFIED_BY_COLUMN_NAMES.add("LAST_MODIFIED_BY");
            MODIFIED_DATE_COLUMN_NAMES.add("LAST_MODIFIED_DATE");

            VERSION_COLUMN_NAMES.add("VERSION");
            VERSION_COLUMN_NAMES.add("REVISION");
        }

        public ColumnDef extract(ResultSet rs) throws SQLException {
            String columnName = rs.getString("COLUMN_NAME");
            String columnLabel = WordUtils.capitalizeFully(columnName.replace("_", " "));
            boolean nullable = rs.getInt("NULLABLE") != 0;
            int dataType = rs.getInt("DATA_TYPE");
            int dataLength = rs.getInt("COLUMN_SIZE");
            int precision = rs.getInt("DECIMAL_DIGITS");

            ColumnDef columnDef = new ColumnDef();


            columnDef.setName(columnName);
            columnDef.setDisplayLabel(columnLabel);
            columnDef.setNullable(nullable);
            columnDef.setLength(dataLength);
            columnDef.setColumnType(getColumnType(columnName, dataType));
            columnDef.setDataType(getDateType(dataType, dataLength, precision));
            LOGGER.debug("Column {}, SQL Data type is {}, and derived data type {}",
                    columnName, dataType, columnDef.getDataType());
            setDefaultBasedOnColumnType(columnDef);

            return columnDef;
        }

        private void setDefaultBasedOnColumnType(ColumnDef columnDef) {
            if (columnDef.getColumnType() != null) {
                columnDef.setShowInList(false);
                columnDef.setInsertable(false);
                columnDef.setUpdatable(false);
                columnDef.setNullable(false);
            } else {
                columnDef.setShowInList(true);
                columnDef.setInsertable(true);
                columnDef.setUpdatable(true);
            }
        }

        ColumnType getColumnType(String columnName, int dataType) {
            String upperColName = columnName.toUpperCase();
            if (this.pkColumns.contains(columnName)) {
                return ColumnType.PRIMARY_KEY;
            } else if (dataType == Types.VARCHAR) {
                if (CREATED_BY_COLUMN_NAMES.contains(upperColName)) {
                    return ColumnType.CREATED_BY;
                } else if (MODIFIED_BY_COLUMN_NAMES.contains(upperColName)) {
                    return ColumnType.LAST_MODIFIED_BY;
                }
            } else if (dataType == Types.TIMESTAMP || dataType == Types.DATE || dataType == Types.TIMESTAMP_WITH_TIMEZONE) {
                if (CREATED_DATE_COLUMN_NAMES.contains(upperColName)) {
                    return ColumnType.CREATED_DATE;
                } else if (MODIFIED_DATE_COLUMN_NAMES.contains(upperColName)) {
                    return ColumnType.LAST_MODIFIED_DATE;
                }
            } else if (dataType == Types.BIGINT || dataType == Types.TIMESTAMP
                    || dataType == Types.NUMERIC || dataType == Types.INTEGER) {
                if (VERSION_COLUMN_NAMES.contains(upperColName)) {
                    return ColumnType.VERSION;
                }
            }
            return ColumnType.REGULAR;
        }

        DataType getDateType(int dataType, int length, int precision) {
            if (dataType == Types.TIMESTAMP || dataType == Types.TIMESTAMP_WITH_TIMEZONE) {
                return DataType.DATETIME;
            } else if (dataType == Types.DATE) {
                return DataType.DATE;
            } else if (dataType == Types.BIGINT || (precision == 0 && dataType == Types.NUMERIC) || dataType == Types.INTEGER || dataType == Types.SMALLINT) {
                if (length == 1) {
                    return DataType.BOOLEAN;
                }
                return DataType.NUMBER;
            } else if (dataType == Types.BIT || dataType == Types.BOOLEAN) {
                return DataType.BOOLEAN;
            } else if (dataType == Types.FLOAT || dataType == Types.DOUBLE
                    || dataType == Types.DECIMAL || dataType == Types.NUMERIC) {
                return DataType.DECIMAL;
            } else if (dataType == Types.VARCHAR || dataType == Types.NVARCHAR || dataType == Types.CHAR) {
                return DataType.STRING;
            }
            return null;
        }
    }

}
