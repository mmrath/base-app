package com.mmrath.sapp.service.core;

import com.mmrath.sapp.domain.core.AccessLevel;
import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.repository.core.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    public Map<Long, Map<AccessLevel, Permission>> findPermissionGroups() {
        Map<Long, Map<AccessLevel, Permission>> permissionGroups = new TreeMap<>();

        List<Permission> permissions = permissionRepository.findAll(new Sort(Sort.Direction.ASC, "resource", "id"));
        permissions.forEach(
                permission -> {
                    if (!permissionGroups.containsKey(permission.getResource().getId())) {
                        permissionGroups.put(permission.getResource().getId(), new HashMap<>());
                    }
                    permissionGroups.get(permission.getResource().getId()).put(permission.getAccessLevel(), permission);
                });


        return permissionGroups;
    }

    public Permission findPermissionById(Long id) {
        return permissionRepository.findOne(id);
    }
}
