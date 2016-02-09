package com.mmrath.sapp.service.data.rsql;


import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.*;

import static com.mmrath.sapp.service.data.rsql.JdbcSpecification.where;

public class JdbcRsqlUtils {

    public static JdbcSpecification parse(String query) {
        Node node = new RSQLParser(JdbcOperation.getOperators()).parse(query);
        JdbcSpecification search = node.accept(new JdbcRsqlVisitor());
        return search;
    }

    public static class JdbcRsqlVisitor extends NoArgRSQLVisitorAdapter<JdbcSpecification> {
        private final JdbcSpecificationBuilder builder;

        public JdbcRsqlVisitor() {
            builder = new JdbcSpecificationBuilder();
        }

        @Override
        public JdbcSpecification visit(AndNode andNode) {
            return builder.createSpecification(andNode);
        }

        @Override
        public JdbcSpecification visit(OrNode orNode) {
            return builder.createSpecification(orNode);
        }

        @Override
        public JdbcSpecification visit(ComparisonNode comparisonNode) {
            return where(comparisonNode.getSelector(),
                    comparisonNode.getOperator(), comparisonNode.getArguments());
        }
    }
}
