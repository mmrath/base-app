package com.mmrath.sapp.service.data.sql;

import com.mmrath.sapp.domain.data.ColumnDef;
import com.mmrath.sapp.domain.data.TableDef;
import com.mmrath.sapp.service.data.DataUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Copied from https://github.com/nurkiewicz/spring-data-jdbc-repository
 *
 * @author Tomasz Nurkiewicz
 */
public class SqlGenerator {

    public static final String WHERE = " WHERE ";
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final String SELECT = "SELECT ";
    public static final String FROM = "FROM ";
    public static final String DELETE = "DELETE ";
    public static final String COMMA = ", ";
    public static final String PARAM = " = ?";

    public SqlGenerator() {
    }

    // Unfortunately {@link org.apache.commons.lang3.StringUtils} not available
    private static String repeat(String s, String separator, int count) {
        StringBuilder string = new StringBuilder((s.length() + separator.length()) * count);
        while (--count > 0) {
            string.append(s).append(separator);
        }
        return string.append(s).toString();
    }

    public String count(TableDef table) {
        return SELECT + "COUNT(*) " + FROM + table.getName();
    }

    public String deleteById(TableDef table) {
        return DELETE + FROM + table.getName() + whereByIdClause(table);
    }

    private String whereByIdClause(TableDef table) {
        final StringBuilder whereClause = new StringBuilder(WHERE);
        whereClause.append(table.getPrimaryKeyColumn().getName()).append(PARAM);
        return whereClause.toString();
    }

    private String whereByIdsClause(TableDef table, int idsCount) {
        final StringBuilder whereClause = new StringBuilder(WHERE);
        return whereClause.
                append(table.getPrimaryKeyColumn().getName()).
                append(" IN (").
                append(repeat("?", COMMA, idsCount)).
                append(")").
                toString();
    }

    public String selectAll(TableDef table) {
        return SELECT + getAllColumnsClause(table) + " " + FROM + table.getName();
    }

    public String selectAll(TableDef table, String whereClause, Pageable page) {
        return selectAll(table, whereClause, page.getSort()) + limitClause(page);
    }

    public String selectAll(TableDef table, String whereClause, Sort sort) {
        String sql = selectAll(table);
        if (StringUtils.hasText(whereClause)) {
            sql = sql + WHERE + whereClause;
        }
        return sql + sortingClauseIfRequired(sort);
    }

    protected String limitClause(Pageable page) {
        final int offset = page.getPageNumber() * page.getPageSize();
        return " LIMIT " + offset + COMMA + page.getPageSize();
    }

    public String selectById(TableDef table) {
        return selectAll(table) + whereByIdClause(table);
    }

    public String selectByIds(TableDef table, int idsCount) {
        switch (idsCount) {
            case 0:
                return selectAll(table);
            case 1:
                return selectById(table);
            default:
                return selectAll(table) + whereByIdsClause(table, idsCount);
        }
    }

    protected String sortingClauseIfRequired(Sort sort) {
        if (sort == null) {
            return "";
        }
        StringBuilder orderByClause = new StringBuilder();
        orderByClause.append(" ORDER BY ");
        for (Iterator<Sort.Order> iterator = sort.iterator(); iterator.hasNext(); ) {
            final Sort.Order order = iterator.next();
            orderByClause.
                    append(order.getProperty()).
                    append(" ").
                    append(order.getDirection().toString());
            if (iterator.hasNext()) {
                orderByClause.append(COMMA);
            }
        }
        return orderByClause.toString();
    }

    public String update(TableDef table) {
        final StringBuilder updateQuery = new StringBuilder("UPDATE " + table.getName() + " SET ");
        List<ColumnDef> columns = table.getColumns();
        boolean columnAdded = false;
        for (Iterator<ColumnDef> iterator = columns.iterator(); iterator.hasNext(); ) {
            ColumnDef column = iterator.next();
            if (DataUtils.isAllowedInUpdateClause(column)) {
                if (columnAdded) {
                    updateQuery.append(COMMA);
                }
                columnAdded = true;
                updateQuery.append(column.getName()).append(" = ?");
            }
        }
        updateQuery.append(whereByIdClause(table));
        return updateQuery.toString();
    }

    public String create(TableDef table) {
        final StringBuilder createQuery = new StringBuilder("INSERT INTO " + table.getName() + " (");
        List<ColumnDef> columns = table.getColumns();
        boolean columnAdded = false;
        int totalColumnsAdded = 0;
        for (Iterator<ColumnDef> iterator = columns.iterator(); iterator.hasNext(); ) {
            ColumnDef column = iterator.next();
            if (DataUtils.isAllowedInInsertClause(column)) {
                if (columnAdded) {
                    createQuery.append(COMMA);
                }
                columnAdded = true;
                totalColumnsAdded = totalColumnsAdded + 1;
                createQuery.append(column.getName());
            }
        }
        createQuery.append(")").append(" VALUES (");
        if (totalColumnsAdded > 0) {
            createQuery.append(repeat("?", COMMA, totalColumnsAdded));
        }
        return createQuery.append(")").toString();
    }

    public String deleteAll(TableDef table) {
        return DELETE + FROM + table.getName();
    }

    public String countById(TableDef table) {
        return count(table) + whereByIdClause(table);
    }

    public String getAllColumnsClause(TableDef table) {
        StringBuilder columnsQry = new StringBuilder();
        for (Iterator<ColumnDef> iterator = table.getColumns().iterator(); iterator.hasNext(); ) {
            ColumnDef column = iterator.next();
            columnsQry.append(column.getName());
            if (iterator.hasNext()) {
                columnsQry.append(COMMA);
            }
        }
        return columnsQry.toString();
    }
}