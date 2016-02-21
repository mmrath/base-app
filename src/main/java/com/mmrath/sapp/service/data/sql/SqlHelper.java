package com.mmrath.sapp.service.data.sql;

import com.mmrath.sapp.domain.data.TableDef;
import org.springframework.data.domain.Pageable;


/**
 * Copied from https://github.com/nurkiewicz/spring-data-jdbc-repository
 * Author: tom
 */
public class SqlHelper {
    public static String ROW_NUM_WRAPPER = "SELECT a__.* FROM (SELECT row_number() OVER (ORDER BY %s) AS ROW_NUM,  t__.*  FROM   (%s) t__) a__ WHERE  a__.row_num BETWEEN %s AND %s";

    public static String generateSelectAllWithPagination(TableDef table, Pageable page, SqlGenerator sqlGenerator) {
        final int beginOffset = page.getPageNumber() * page.getPageSize() + 1;
        final int endOffset = beginOffset + page.getPageSize() - 1;
        String orderByPart = page.getSort() != null ? page.getSort().toString().replace(":", "") : table.getPrimaryKeyColumn().getColumnName();
        String selectAllPart = sqlGenerator.selectAll(table);
        return String.format(ROW_NUM_WRAPPER, orderByPart, selectAllPart, beginOffset, endOffset);
    }
}