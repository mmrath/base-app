package com.mmrath.sapp.domain.data;

import com.mmrath.sapp.domain.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "t_table_def")
public class TableDef extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Pattern(regexp = "[a-z0-9-]{3,25}", message = "Table tag can only contain lower case letters, number and hyphen")
    @NotNull
    @Size(min = 3, max = 25, message = "Table tag must be of length between {min} and {max} characters")
    @Column(name = "tag_id", nullable = false, length = 32, unique = true)
    private String tagId;

    @Pattern(regexp = "[A-Za-z0-9_]*")
    @NotNull
    @Size(min = 3, max = 64)
    @Column(name = "table_name", nullable = false, length = 64, unique = true)
    private String tableName;

    @Column(name = "display_name", nullable = false, length = 128)
    private String displayName;

    @Column(name = "insertable", nullable = false)
    private Boolean insertable;

    @Column(name = "updatable", nullable = false)
    private Boolean updatable;

    @Column(name = "deletable", nullable = false)
    private Boolean deletable;

    @Column(name = "multi_selectable", nullable = false)
    private Boolean multiSelectable;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private List<ColumnDef> columns;

    @Transient
    private ColumnDef primaryKeyColumn;

    public ColumnDef getPrimaryKeyColumn() {
        if (this.columns != null) {
            for (ColumnDef columnDef : columns) {
                if (columnDef.getColumnType() == ColumnType.PRIMARY_KEY) {
                    return columnDef;
                }
            }
        }
        return null;
    }



    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public Boolean getDeletable() {
        return deletable;
    }

    public void setDeletable(Boolean deletable) {
        this.deletable = deletable;
    }

    public Boolean getMultiSelectable() {
        return multiSelectable;
    }

    public void setMultiSelectable(Boolean multiSelectable) {
        this.multiSelectable = multiSelectable;
    }

    public List<ColumnDef> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDef> columns) {
        this.columns = columns;
    }
}
