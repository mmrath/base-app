package com.mmrath.sapp.domain.core;


import com.mmrath.sapp.domain.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Model object that represents a security role.
 */
@Entity
@Cacheable
@Table(name = "t_role")
@NamedQueries({
        @NamedQuery(name = Role.QUERY_GET_PERMISSIONS,
                query = "SELECT p FROM Permission p,Role t where p member of t.permissions and t.id = :id"),
        @NamedQuery(name = Role.QUERY_GET_UNASSIGNED_PERMISSIONS,
                query = "SELECT p FROM Permission p,Role t where p not member of t.permissions and t.id = :id")})
public class Role extends AbstractAuditingEntity<Long> {

    public static final String QUERY_GET_PERMISSIONS = "Role.getPermissions";

    public static final String QUERY_GET_UNASSIGNED_PERMISSIONS = "Role.getUnassignedPermissions";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 30, message = "Role name must be between {min} to {max} character long")
    @Pattern(regexp = "[a-zA-Z_]+",
            message = "Role name can only contain letters, digits and underscore(_)")
    private String name;

    @NotNull
    @Size(min = 4, max = 64,
            message = "Role description must be between {min} to {max} character long")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_permission", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions = new ArrayList<>();

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description
                + '\'' + ", permissions=" + permissions + "} " + super.toString();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
