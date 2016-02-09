package com.mmrath.sapp.service.data.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.repeat;

public class JdbcSpecification {
    private final String sql;
    private final List<String> arguments;

    public JdbcSpecification(String sql, List<String> arguments) {
        this.sql = sql;
        this.arguments = arguments;
    }

    public static JdbcSpecification where(String selector, ComparisonOperator operator, List<String> arguments) {
        switch (JdbcOperation.getSimpleOperator(operator)) {
            case EQUAL:
                return getSingleValuedSpecification("%s = ?", selector, arguments);

            case NOT_EQUAL:
                return getSingleValuedSpecification("%s != ?", selector, arguments);

            case LIKE:
                return getSingleValuedSpecification("%s LIKE ?", selector, arguments);

            case NOT_LIKE:
                return getSingleValuedSpecification("%s NOT LIKE ?", selector, arguments);

            case GREATER_THAN:
                return getSingleValuedSpecification("%s > ?", selector, arguments);

            case GREATER_THAN_OR_EQUAL:
                return getSingleValuedSpecification("%s >= ?", selector, arguments);

            case LESS_THAN:
                return getSingleValuedSpecification("%s < ?", selector, arguments);

            case LESS_THAN_OR_EQUAL:
                return getSingleValuedSpecification("%s <= ?", selector, arguments);

            case IN:
                return getMultiValuedSpecification(selector, "IN", arguments);

            case NOT_IN:
                return getMultiValuedSpecification(selector, "NOT IN", arguments);
        }
        return null;
    }

    private static JdbcSpecification getSingleValuedSpecification(String format, String property, List<String> args) {
        Assert.isTrue(args.size() == 1);
        return new JdbcSpecification(format(format, property), Collections.unmodifiableList(args));
    }

    private static JdbcSpecification getMultiValuedSpecification(String property, String operator, List<String> arguments) {
        List<String> args = arguments.stream().filter(value -> value != null).collect(toList());
        Assert.isTrue(args.size() >= 1);
        return new JdbcSpecification(format("%s %s (%s)", property, operator, repeat("? ", args.size())),
                Collections.unmodifiableList(args));
    }

    public JdbcSpecification and(JdbcSpecification rhs) {
        List<String> resultArgs = new ArrayList<>();
        resultArgs.addAll(arguments);
        resultArgs.addAll(rhs.arguments);
        return new JdbcSpecification(format("(%s) AND (%s)", sql, rhs.sql), Collections.unmodifiableList(resultArgs));
    }

    public JdbcSpecification or(JdbcSpecification rhs) {
        List<String> resultArgs = new ArrayList<>();
        resultArgs.addAll(arguments);
        resultArgs.addAll(rhs.arguments);
        return new JdbcSpecification(format("(%s) OR (%s)", sql, rhs.sql), Collections.unmodifiableList(resultArgs));
    }

    public String getSql() {
        return sql;
    }

    public List<String> getArguments() {
        return arguments;
    }
}
