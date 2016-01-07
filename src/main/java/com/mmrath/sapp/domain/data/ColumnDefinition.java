package com.mmrath.sapp.domain.data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "t_column_definition")
public class ColumnDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "column_index", nullable = false)
    private Integer columnIndex;

    @Pattern(regexp = "[a-zA-Z0-9_]+")
    @Column(name = "column_name", nullable = false, length = 64)
    private String columnName;

    @Column(name = "display_name", nullable = false, length = 64)
    private String displayName;

    @Column(name = "nullable", nullable = false)
    private Boolean nullable;

    @Column(name = "insertable", nullable = false)
    private Boolean insertable;

    @Column(name = "updatable", nullable = false)
    private Boolean updatable;

    @Column(name = "searchable", nullable = false)
    private Boolean searchable;

    @Column(name = "sortable", nullable = false)
    private Boolean sortable;

    @Column(name = "visible_in_list", nullable = false)
    private Boolean visibleInList;

    @Column(name = "data_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @Column(name = "column_type")
    @Enumerated(EnumType.STRING)
    private ColumnType columnType;

    @Column(name = "length")
    private Integer length;//Data type Length

    @Column(name = "data_cell_template", length = 511)
    private String dataCellTemplate;

    @Column(name = "header_cell_template", length = 511)
    private String headerCellTemplate;

    @Column(name = "valid_pattern", length = 255)
    private String validPattern;

    @Column(name = "valid_pattern_message", length = 511)
    private String validPatternMessage;

    @Column(name = "default_value")
    private String defaultValue;


}
