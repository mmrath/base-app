package com.mmrath.sapp.domain.data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

//@Entity
//@Table(name="t_column_definition")
public class ColumnDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="column_index", nullable = false)
    private Long columnIndex;

    @Column(name="column_name", nullable = false, length = 64)
    private String columnName;

    @Column(name="display_name", nullable = false, length = 64)
    private String displayName;

    @Column(name="primaryKey", nullable = false)
    private Boolean primaryKey;

    @Column(name="nullable")
    private Boolean nullable;

    @Column(name="insertable", nullable = false)
    private Boolean insertable;

    @Column(name="updatable", nullable = false)
    private Boolean updatable;

    @Column(name="searchable", nullable = false)
    private Boolean searchable;

    @Column(name="sortable", nullable = false)
    private Boolean sortable;

    @Column(name="visible_in_list", nullable=false)
    private Boolean visibleInList;

    @Column(name="data_type", nullable = false)
    private String dataType;

    @Column(name="length")
    private Integer length;//Data type Length

    @Column(name="data_cell_template", length = 255)
    private String dataCellTemplate;

    @Column(name="header_cell_template", length = 255)
    private String headerCellTemplate;

    @Column(name="valid_pattern")
    private String validPattern;

    @Column(name="valid_pattern_message")
    private String validPatternErrorMessage;

    @Column(name="default_value")
    private String defaultValue;

    @Column(name="audit_column_type")
    @Enumerated(EnumType.STRING)
    private AuditColumnType auditColumnType;
}
