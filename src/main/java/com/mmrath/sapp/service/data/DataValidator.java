package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.ColumnDef;
import com.mmrath.sapp.domain.data.ColumnType;
import com.mmrath.sapp.domain.data.TableDef;

import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class DataValidator {


    /**
     * Validate the input values
     *
     * @param table  table definition
     * @param values values map
     * @param mode   insert or update mode
     */
    public static final void validate(TableDef table, Map<String, Object> values, boolean mode) {
        for (ColumnDef column : table.getColumns()) {
            Object colValue = values.get(column.getCodeName());
            validate(column, colValue, mode);
        }
    }

    private static void validate(ColumnDef column, Object colValue, boolean insertMode) {
        if (column.getColumnType() == ColumnType.PRIMARY_KEY && !insertMode && colValue == null) {
            throw new ValidationException("Primary key required for updating value");
        }

        if (column.getColumnType() == ColumnType.VERSION && !insertMode && colValue == null) {
            throw new ValidationException("Version column value is required for updating value");
        }

        if (!DataUtils.isAuditColumn(column)) {
            if ((column.getInsertable() && insertMode) || (column.getUpdatable() && !insertMode)) {
                validate(column, colValue);
            }
        }
    }

    public static final void validate(ColumnDef column, Object value) {

        if (!column.getNullable() && value == null) {
            throw new ValidationException(format("Null/empty value not allowed for %s", column.getCodeName()));
        }
        if (column.getValidPattern() != null && value != null) {
            if (!Pattern.matches(column.getValidPattern(), value.toString())) {
                throw new ValidationException(column.getValidPatternMessage());
            }
        }
    }
}
