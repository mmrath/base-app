package com.mmrath.sapp.domain.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "t_column_def")
public class ColumnDef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "index", nullable = false)
    private Integer index;

    @Pattern(regexp = "[a-zA-Z0-9_]+")
    @Column(name = "column_name", nullable = false, length = 64)
    private String columnName;

    //A friendly name to hide column name. Can be used in search queries
    @Pattern(regexp = "[a-z][a-zA-Z0-9]+")
    @Column(name = "code_name", nullable = false, length = 64)
    private String codeName;

    @Column(name = "display_label", nullable = false, length = 64)
    private String displayLabel;

    @Column(name = "searchable", nullable = false)
    private Boolean searchable;

    @Column(name = "sortable", nullable = false)
    private Boolean sortable;

    @Column(name = "show_in_list", nullable = false)
    private Boolean showInList;

    @Column(name = "data_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DataType dataType;

    @Column(name = "column_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ColumnType columnType;


    @Column(name = "data_template", length = 256)
    private String dataTemplate;

    @Column(name = "header_template", length = 256)
    private String headerTemplate;


    @Override
    public String toString() {
        return "ColumnDef{" +
                "id=" + id +
                ", index=" + index +
                ", name='" + columnName + '\'' +
                ", code='" + codeName + '\'' +
                ", displayLabel='" + displayLabel + '\'' +
                ", searchable=" + searchable +
                ", sortable=" + sortable +
                ", showInList=" + showInList +
                ", dataType=" + dataType +
                ", columnType=" + columnType +
                ", dataTemplate='" + dataTemplate + '\'' +
                ", headerTemplate='" + headerTemplate + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
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

    public Boolean getShowInList() {
        return showInList;
    }

    public void setShowInList(Boolean showInList) {
        this.showInList = showInList;
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

    public String getDataTemplate() {
        return dataTemplate;
    }

    public void setDataTemplate(String dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    public String getHeaderTemplate() {
        return headerTemplate;
    }

    public void setHeaderTemplate(String headerTemplate) {
        this.headerTemplate = headerTemplate;
    }

}
