package com.mmrath.sapp.web.rest.core;

import com.mmrath.sapp.domain.core.AccessLevel;
import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Resource;
import com.mmrath.sapp.repository.core.ResourceRepository;
import com.mmrath.sapp.service.core.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/core/permissions")
public class PermissionResource {

    private final PermissionService permissionService;
    private final ResourceRepository resourceRepository;

    @Autowired
    public PermissionResource(PermissionService permissionService, ResourceRepository resourceRepository) {
        this.permissionService = permissionService;
        this.resourceRepository = resourceRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Permission> getPermissions() {
        return permissionService.findAllPermissions();
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Map<String, Map<AccessLevel, Permission>> getPermissionGroups() {
        return permissionService.findPermissionGroups();
    }

    @RequestMapping(value = "/accessLevels", method = RequestMethod.GET)
    public List<AccessLevel> getAccessLevels() {
        return Arrays.asList(AccessLevel.values());
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public List<Resource> getResources() {
        return resourceRepository.findAll();
    }

}
