package com.mmrath.sapp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmrath.sapp.domain.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_user")
@NamedQueries({@NamedQuery(name = User.QUERY_FIND_BY_USER_NAME,
        query = "select t from User t where lower(t.username) = lower(:username)"),
        @NamedQuery(name = User.QUERY_FIND_ROLES,
                query = "select t.roles from User t where t.id = :id"),
        @NamedQuery(name = User.QUERY_FIND_ALL_PERMISSIONS,
                query = "select distinct(p) from User u, Permission p, Role r "
                        + "where u.id = :id and ( r member of u.roles and p member of r.permissions)")})
public class User extends AbstractAuditingEntity<Long> {

    public static final String QUERY_FIND_BY_USER_NAME = "User.findByUsernameQ";

    public static final String QUERY_FIND_ROLES = "User.findRoles";

    public static final String QUERY_FIND_ALL_PERMISSIONS = "User.findAllPermissions";

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToOne(optional = true, cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_credential",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "credential_id"))
    public Credential credential;

    @Id
    @TableGenerator(name = "userIdGen", table = "SEQUENCE_TABLE", pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_VALUE", pkColumnValue = "USER_ID_SEQ", initialValue = 101,
            allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "userIdGen")
    private Long id;

    @Pattern(regexp = "[\\w_\\.]+")
    @Column(name = "username", length = 30, unique = true, nullable = false)
    @Size(min = 4, max = 30)
    private String username;

    @Column(name = "first_name")
    @Pattern(regexp = "[\\w_\\.]+")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "[\\w_\\.]+")
    private String lastName;

    @Column(name = "email", unique = true, length = 64, nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean enabled;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", " + super.toString() +
                "}";
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

}