package com.mmrath.sapp.domain.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


//This is DTO to hold list of permissions applicable on a Resource. This does not map to a table.
public class PermissionGroup implements Serializable {

    private final Resource resource;

    private final Map<AccessLevel, Permission> permissions = new HashMap<>();

    public PermissionGroup(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean add(Permission permission) {
        if (permission.getResource() != resource) {
            throw new IllegalArgumentException("This permission groups is not for the resource" + permission.getResource());
        }
        if (!permissions.containsKey(permission.getAccessLevel())) {
            permissions.put(permission.getAccessLevel(), permission);
            return true;
        }
        return false;
    }

    public Map<AccessLevel, Permission> getPermissions() {
        return Collections.unmodifiableMap(permissions);
    }
}
