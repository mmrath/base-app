package com.mmrath.sapp.service.data.sql;

import com.mmrath.sapp.domain.data.ColumnDef;
import com.mmrath.sapp.domain.data.DataType;
import com.mmrath.sapp.domain.data.TableDef;
import com.mmrath.sapp.service.data.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Current implementation works only for single column auto generated primary key
 */
public class DataRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataRepository.class);

    private final SqlGenerator sqlGenerator;
    private final JdbcTemplate jdbcOperations;

    public DataRepository(JdbcTemplate jdbcOperations) {
        this(jdbcOperations, new SqlGenerator());
    }

    public DataRepository(JdbcTemplate jdbcOperations, SqlGenerator sqlGenerator) {
        this.jdbcOperations = jdbcOperations;
        this.sqlGenerator = sqlGenerator;
    }

    public long count(TableDef table) {
        return jdbcOperations.queryForObject(sqlGenerator.count(table), Long.class);
    }

    public int delete(TableDef table, Serializable id) {
        String deleteSql = sqlGenerator.deleteById(table);
        logger.debug("Delete SQL:[{}]", deleteSql);
        return jdbcOperations.update(deleteSql, id);
    }

    public void delete(TableDef table, Iterable<? extends Serializable> ids) {
        for (Serializable id : ids) {
            delete(table, id);
        }
    }

    public boolean exists(TableDef table, Serializable id) {
        return jdbcOperations.queryForObject(sqlGenerator.countById(table), Integer.class, id) > 0;
    }

    public List<Map<String, Object>> findAll(TableDef table) {
        return jdbcOperations.query(sqlGenerator.selectAll(table), new DataRowMapper(table));
    }

    public Map<String, Object> findOne(TableDef table, Serializable id) {
        final List<Map<String, Object>> entityOrEmpty =
                jdbcOperations.query(sqlGenerator.selectById(table), new DataRowMapper(table), id);
        return entityOrEmpty.isEmpty() ? null : entityOrEmpty.get(0);
    }

    public Page<Map<String, Object>> findAll(TableDef table, String whereClause, Pageable page) {
        if (page == null) {
            throw new IllegalArgumentException("PageRequest cannot be null");
        }
        String query = sqlGenerator.selectAll(table, whereClause, page);
        logger.trace("FindAll SQL: {}", query);
        return new PageImpl<Map<String, Object>>(jdbcOperations.query(query, new DataRowMapper(table)), page, count(table));
    }


    public Map<String, Object> update(TableDef table, Map<String, Object> values) {
        String updateSql = sqlGenerator.update(table);
        logger.trace("Update SQL: {}", updateSql);

        ArrayList<Object> params = new ArrayList<>();
        Map<String, Object> updatedValues = new HashMap<>();

        table.getColumns().stream()
                .filter(column -> DataUtils.isAllowedInUpdateClause(column))
                .forEach(column -> {
                    Object value = DataUtils.getValueForUpdate(column, values);
                    updatedValues.put(column.getCodeName(), value);
                    params.add(value);
                });
        params.add(values.get(table.getPrimaryKeyColumn().getCodeName()));
        updatedValues.put(table.getPrimaryKeyColumn().getCodeName(), values.get(table.getPrimaryKeyColumn().getCodeName()));
        ColumnDef versionCol = table.getVersionColumn();
        if (versionCol != null) {
            params.add(values.get(table.getVersionColumn().getCodeName()));
        }
        final Object[] paramsArray = params.toArray();
        if (logger.isDebugEnabled()) {
            logger.debug("Values {}", Arrays.toString(paramsArray));
        }
        int updatedRows = jdbcOperations.update(con -> {
            PreparedStatement ps = con.prepareStatement(updateSql);
            int colIndex = 0;
            for (Object value : paramsArray) {
                colIndex++;
                StatementCreatorUtils.setParameterValue(ps, colIndex, SqlTypeValue.TYPE_UNKNOWN, value);
            }
            return ps;
        });

        if (updatedRows != 1) {
            throw new RuntimeException("Optimistic lock exception");
        }

        return updatedValues;
    }


    public Map<String, Object> create(TableDef table, Map<String, Object> values) {
        String createSql = sqlGenerator.create(table);
        logger.trace("Create SQL: {}", createSql);
        ArrayList<Object> params = new ArrayList<>();
        Map<String, Object> insertedValues = new HashMap<>();

        table.getColumns().stream()
                .filter(column -> DataUtils.isAllowedInInsertClause(column))
                .forEach(column -> {
                    Object value = DataUtils.getValueForInsert(column, values);
                    insertedValues.put(column.getCodeName(), value);
                    params.add(value);
                });
        final Object[] paramsArray = params.toArray();
        if (logger.isDebugEnabled()) {
            logger.debug("Values {}", Arrays.toString(paramsArray));
        }
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(con -> {
            PreparedStatement ps = con.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            int colIndex = 0;
            for (Object value : paramsArray) {
                colIndex++;
                StatementCreatorUtils.setParameterValue(ps, colIndex, SqlTypeValue.TYPE_UNKNOWN, value);
            }
            return ps;
        }, keyHolder);
        logger.debug("Generated key: {}", keyHolder.getKey().longValue());
        insertedValues.put(table.getPrimaryKeyColumn().getCodeName(), keyHolder.getKey().longValue());
        return insertedValues;
    }


    public class DataRowMapper implements RowMapper<Map<String, Object>> {
        private final TableDef table;

        public DataRowMapper(TableDef table) {
            this.table = table;
        }

        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> map = new HashMap<>();

            for (ColumnDef column : table.getColumns()) {
                String columnName = column.getColumnName();
                String columnCodeName = column.getCodeName();
                DataType dataType = column.getDataType();
                Object value;
                if (dataType == DataType.BOOLEAN) {
                    value = rs.getBoolean(columnName);
                } else if (dataType == DataType.DATE) {
                    value = rs.getDate(columnName);
                    if (value != null) {
                        value = new Date(((java.sql.Date) value).getTime());
                    }
                } else if (dataType == DataType.DATETIME) {
                    value = rs.getTimestamp(columnName);
                    if (value != null) {
                        value = new Date(((java.sql.Timestamp) value).getTime());
                    }
                } else if (dataType == DataType.STRING || dataType == DataType.PASSWORD) {
                    value = rs.getString(columnName);
                } else if (dataType == DataType.NUMBER) {
                    value = rs.getLong(columnName);
                } else if (dataType == dataType.DECIMAL) {
                    value = rs.getBigDecimal(columnName);
                } else {
                    value = rs.getObject(columnName);
                }
                map.put(columnCodeName, value);
            }
            return map;
        }
    }
}
