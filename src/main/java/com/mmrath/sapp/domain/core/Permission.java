package com.mmrath.sapp.domain.core;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cacheable
@Table(name = "t_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", updatable = false, nullable = false)
    private Resource resource;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false,
            updatable = false, insertable = false)
    private AccessLevel accessLevel;

    @Column(name = "description", nullable = false)
    private String description;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return resource.getName() + ":" + accessLevel.name();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", resource=" + resource +
                ", accessLevel=" + accessLevel +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Permission that = (Permission) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
