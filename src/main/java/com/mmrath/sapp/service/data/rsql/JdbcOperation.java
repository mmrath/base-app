package com.mmrath.sapp.service.data.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by murali on 4/02/2016.
 */
public enum JdbcOperation {
    EQUAL(new ComparisonOperator(new String[]{"=", "=="})),
    NOT_EQUAL(new ComparisonOperator(new String[]{"!=", "<>"})),
    LIKE(new ComparisonOperator(new String[]{"~"})),
    NOT_LIKE(new ComparisonOperator(new String[]{"!~"})),
    GREATER_THAN(new ComparisonOperator(new String[]{">"})),
    GREATER_THAN_OR_EQUAL(new ComparisonOperator(new String[]{">="})),
    LESS_THAN(new ComparisonOperator(new String[]{"<"})),
    LESS_THAN_OR_EQUAL(new ComparisonOperator(new String[]{"<="})),
    IN(new ComparisonOperator("in", true)),
    NOT_IN(new ComparisonOperator("!in", true));

    private ComparisonOperator operator;

    JdbcOperation(ComparisonOperator operator) {
        this.operator = operator;
    }

    public static JdbcOperation getSimpleOperator(ComparisonOperator operator) {
        for (JdbcOperation operation : values()) {
            if (operation.getOperator() == operator) {
                return operation;
            }
        }
        return null;
    }

    public static Set<ComparisonOperator> getOperators() {
        HashSet<ComparisonOperator> operators = new HashSet();
        for (JdbcOperation operation : JdbcOperation.values()) {
            operators.add(operation.getOperator());
        }
        return operators;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }
}
