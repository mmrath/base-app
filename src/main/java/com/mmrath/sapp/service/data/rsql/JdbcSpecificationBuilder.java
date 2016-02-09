package com.mmrath.sapp.service.data.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;

import java.util.ArrayList;
import java.util.List;

import static com.mmrath.sapp.service.data.rsql.JdbcSpecification.where;

public class JdbcSpecificationBuilder {

    public JdbcSpecification createSpecification(Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        return null;
    }

    public JdbcSpecification createSpecification(LogicalNode logicalNode) {
        List<JdbcSpecification> specs = new ArrayList<JdbcSpecification>();
        JdbcSpecification temp;
        for (Node node : logicalNode.getChildren()) {
            temp = createSpecification(node);
            if (temp != null) {
                specs.add(temp);
            }
        }

        JdbcSpecification result = specs.get(0);
        if (logicalNode.getOperator() == LogicalOperator.AND) {
            for (int i = 1; i < specs.size(); i++) {
                result = result.and(specs.get(i));
            }
        } else if (logicalNode.getOperator() == LogicalOperator.OR) {
            for (int i = 1; i < specs.size(); i++) {
                result = result.or(specs.get(i));
            }
        }
        return result;
    }

    public JdbcSpecification createSpecification(ComparisonNode comparisonNode) {
        JdbcSpecification result = where(comparisonNode.getSelector(),
                comparisonNode.getOperator(), comparisonNode.getArguments());
        return result;
    }
}
