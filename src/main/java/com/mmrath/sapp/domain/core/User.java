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
@NamedQueries({@NamedQuery(name = User.QUERY_FIND_BY_LOGIN,
        query = "select t from User t where lower(t.login) = lower(:login)"),
        @NamedQuery(name = User.QUERY_FIND_ROLES,
                query = "select t.roles from User t where t.id = :id"),
        @NamedQuery(name = User.QUERY_FIND_ALL_PERMISSIONS,
                query = "select distinct(p) from User u, Permission p, Role r "
                        + "where u.id = :id and ( r member of u.roles and p member of r.permissions)")})
public class User extends AbstractAuditingEntity<Long> {

    public static final String QUERY_FIND_BY_LOGIN = "User.findByLogin";

    public static final String QUERY_FIND_ROLES = "User.findRoles";

    public static final String QUERY_FIND_ALL_PERMISSIONS = "User.findAllPermissions";

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @PrimaryKeyJoinColumn
    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    public Credential credential;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?",
            message = "Invalid username")
    @Column(name = "login", length = 64, unique = true, nullable = false)
    @Size(min = 4, max = 30)
    private String login;

    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    @Pattern(regexp = "[\\p{L} .'-]+", message = "Invalid first name")
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name")
    @Pattern(regexp = "[\\p{L} .'-]*", message = "Invalid last name")
    private String lastName;

    @Size(max = 64)
    @Pattern(regexp = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})",
            message = "Invalid email address")
    @Column(name = "email", unique = true, length = 64, nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean enabled;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", langKey='" + langKey + '\'' +
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

    public String getLogin() {
        return login;
    }

    public void setUsername(String login) {
        this.login = login;
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

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
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