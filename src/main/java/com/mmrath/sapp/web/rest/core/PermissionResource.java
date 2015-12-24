package com.mmrath.sapp.web.rest.core;

import com.mmrath.sapp.domain.core.AccessLevel;
import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Resource;
import com.mmrath.sapp.service.core.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/permissions")
public class PermissionResource {

    private final PermissionService permissionService;

    @Autowired
    public PermissionResource(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Permission> getPermissions() {
        return permissionService.findAllPermissions();
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public Map<Resource, Map<AccessLevel, Permission>> getPermissionGroups() {
        return permissionService.findPermissionGroups();
    }

    @RequestMapping(value = "/accessLevels", method = RequestMethod.GET)
    public List<AccessLevel> getAccessLevels() {
        return Arrays.asList(AccessLevel.values());
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public List<Resource> getResources() {
        return Arrays.asList(Resource.values());
    }

}
