package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.ColumnDef;
import com.mmrath.sapp.domain.data.ColumnType;
import com.mmrath.sapp.domain.data.TableDef;

import java.util.Map;

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
    }
}
