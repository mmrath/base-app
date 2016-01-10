package com.mmrath.sapp.domain.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "t_column_def")
public class ColumnDef {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
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

    //Audit or version column type
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getInsertable() {
        return insertable;
    }

    public void setInsertable(Boolean insertable) {
        this.insertable = insertable;
    }

    public Boolean getUpdatable() {
        return updatable;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public Boolean getVisibleInList() {
        return visibleInList;
    }

    public void setVisibleInList(Boolean visibleInList) {
        this.visibleInList = visibleInList;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getDataCellTemplate() {
        return dataCellTemplate;
    }

    public void setDataCellTemplate(String dataCellTemplate) {
        this.dataCellTemplate = dataCellTemplate;
    }

    public String getHeaderCellTemplate() {
        return headerCellTemplate;
    }

    public void setHeaderCellTemplate(String headerCellTemplate) {
        this.headerCellTemplate = headerCellTemplate;
    }

    public String getValidPattern() {
        return validPattern;
    }

    public void setValidPattern(String validPattern) {
        this.validPattern = validPattern;
    }

    public String getValidPatternMessage() {
        return validPatternMessage;
    }

    public void setValidPatternMessage(String validPatternMessage) {
        this.validPatternMessage = validPatternMessage;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
