package com.mmrath.sapp.web.rest.core;

import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Role;
import com.mmrath.sapp.service.core.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/core/roles")
public class RoleResource {

    private final Logger logger = LoggerFactory.getLogger(RoleResource.class);
    private final RoleService roleService;

    @Autowired
    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<Role> findAll(Pageable pageRequest) {
        return roleService.findAllRoles(pageRequest);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Role create(@RequestBody @Valid Role role) {
        logger.info("Role {}", role);
        role = roleService.createRole(role);
        return role;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Role update(@PathVariable("id") Long id,
                       @Valid @RequestBody Role role) {
        role = roleService.updateRole(role);
        return role;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Role findById(@PathVariable("id") Long id) {
        Role role = roleService.findRoleById(id);
        return role;
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
    @ResponseBody
    public List<Permission> findAssignedPermissions(@PathVariable("id") final Long id,
                                                    @RequestParam(defaultValue = "false") final boolean unassigned) {
        if (!unassigned) {
            return roleService.findRolePermissions(id);
        } else {
            return roleService.findPermissionsUnassignedToRole(id);
        }
    }

}
