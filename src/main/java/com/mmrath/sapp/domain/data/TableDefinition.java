package com.mmrath.sapp.domain.data;

import com.mmrath.sapp.domain.AbstractAuditingEntity;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//@Entity
//@Table(name="t_table_definition")
public class TableDefinition extends AbstractAuditingEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[a-z0-9_]*")
    @NotNull
    @Size(min = 3, max = 64)
    @Column(name="table_name", nullable = false, length = 64, unique = true)
    private String tableName;

    @Column(name="display_name", nullable = false, length = 128)
    private String displayName;

    @Column(name="insertable", nullable = false)
    private Boolean insertable;

    @Column(name="updatable", nullable = false)
    private Boolean updatable;

    @Column(name="deletable", nullable = false)
    private Boolean deletable;

    @Column(name="multi_selectable", nullable = false)
    private Boolean multiSelectable;

    @Override
    public Long getId() {
        return id;
    }


}
