package com.mmrath.sapp.domain.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mmrath.sapp.domain.AbstractAuditingEntity;

/**
 * @author Murali
 */
@Entity
@Cacheable
@Table(name = "t_user_group")
public class UserGroup extends AbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 30)
    private String name;

    @NotNull
    @Size(min = 4, max = 64)
    private String description;

    @ManyToMany
    @JoinTable(name = "t_user_group_role", joinColumns = @JoinColumn(name = "group_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id") )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "t_user_group_user", joinColumns = @JoinColumn(name = "group_id") ,
            inverseJoinColumns = @JoinColumn(name = "user_id") )
    private List<Role> users = new ArrayList<>();

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getUsers() {
        return users;
    }

    public void setUsers(List<Role> users) {
        this.users = users;
    }
}
