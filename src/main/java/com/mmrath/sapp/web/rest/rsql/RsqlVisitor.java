package com.mmrath.sapp.web.rest.rsql;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.NoArgRSQLVisitorAdapter;
import cz.jirutka.rsql.parser.ast.OrNode;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by murali on 25/06/15.
 */
public class RsqlVisitor<T> extends NoArgRSQLVisitorAdapter<Specification<T>> {
    private final RsqlSpecificationBuilder<T> builder;

    public RsqlVisitor() {
        this(new RsqlSpecificationBuilder<T>());
    }

    public RsqlVisitor(RsqlSpecificationBuilder<T> builder) {
        this.builder = builder;
    }


    @Override
    public Specification<T> visit(AndNode andNode) {
        return builder.createSpecification(andNode);
    }

    @Override
    public Specification<T> visit(OrNode orNode) {
        return builder.createSpecification(orNode);
    }

    @Override
    public Specification<T> visit(ComparisonNode comparisonNode) {
        return builder.createSpecification(comparisonNode);
    }
}
