package com.mmrath.sapp.domain.core;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Represents a resource that should be protected. A resource and access level on the resource
 * together called a permission. Resource name and access level can use directly(hard coded) in code.
 * <p>
 * A set of permissions constitute Role, which is high level and no role names should be hard coded
 * in code. Only developers are allowed to write to this table.
 */
@Table(name = "t_resource")
@Cacheable
@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 64)
    @Pattern(regexp = "[A-Z0-9_]*")
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

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
}
