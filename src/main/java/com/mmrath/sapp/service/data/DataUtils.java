package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.ColumnDef;

import java.util.Date;
import java.util.Map;

/**
 * Created by murali on 2/02/2016.
 */
public abstract class DataUtils {

    public static boolean isAllowedInInsertClause(ColumnDef columnDef) {
        switch (columnDef.getColumnType()) {
            case CREATED_BY:
            case CREATED_DATE:
            case LAST_MODIFIED_BY:
            case LAST_MODIFIED_DATE:
            case VERSION:
                return true;
            case REGULAR:
                return columnDef.getInsertable();
            case PRIMARY_KEY:
                return false;
            default:
                throw new IllegalArgumentException("Column type invalid:" + columnDef.getColumnType());
        }
    }

    public static boolean isAllowedInUpdateClause(ColumnDef columnDef) {
        switch (columnDef.getColumnType()) {
            case CREATED_BY:
            case CREATED_DATE:
                return false;
            case LAST_MODIFIED_BY:
            case LAST_MODIFIED_DATE:
            case VERSION:
                return true;
            case REGULAR:
                return columnDef.getUpdatable();
            case PRIMARY_KEY:
                return false;
            default:
                throw new IllegalArgumentException("Column type invalid:" + columnDef.getColumnType());
        }
    }


    public static Object getValueForInsert(ColumnDef column, Map<String, Object> values) {
        switch (column.getColumnType()) {
            case CREATED_BY:
                return "created by";
            case CREATED_DATE:
                return new Date(System.currentTimeMillis());
            case LAST_MODIFIED_BY:
                return "modified by";
            case LAST_MODIFIED_DATE:
                return new Date(System.currentTimeMillis());
            case VERSION:
                return 0;
            case REGULAR:
                return values.get(column.getName());
            case PRIMARY_KEY:
                return null;
            default:
                throw new IllegalArgumentException("Column type invalid:" + column.getColumnType());
        }
    }
}
