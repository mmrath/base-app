package com.mmrath.sapp.web.rest.rsql;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by murali on 25/06/15.
 */
public class RsqlUtils {

    public static <T> Optional<Specification<T>> parse(String search, RsqlVisitor<T> visitor){
        Specification<T> spec = null;
        if(StringUtils.hasText(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new RsqlVisitor<>());
        }
        return Optional.ofNullable(spec);
    }

    public static <T> Optional<Specification<T>> parse(String search){
        return parse(search, new RsqlVisitor<>());
    }
}
