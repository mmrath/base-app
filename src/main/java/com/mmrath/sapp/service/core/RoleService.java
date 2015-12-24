/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmrath.sapp.service.core;

import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Role;
import com.mmrath.sapp.repository.core.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Component
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Permission> findRolePermissions(Long roleId) {
        List<Permission> permissions = roleRepository.findOne(roleId).getPermissions();
        return permissions;
    }

    @Transactional(readOnly = true)
    public List<Permission> findPermissionsUnassignedToRole(Long roleId) {
        return roleRepository.findAllUnassignedPermissions(roleId);
    }

    @Transactional
    public Role createRole(Role role) {
        logger.debug("Saving role {}", role);
        role.setName(role.getName().toUpperCase(Locale.getDefault()).trim());
        role = roleRepository.save(role);

        return role;
    }

    @Transactional
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    @Transactional
    public List<Role> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles;
    }

    @Transactional(readOnly = true)
    public Role findRoleById(Long id) {
        Role role = roleRepository.findOne(id);
        if (role != null) {
            logger.debug("Found role {} with {} permissions", role.getId(),
                    role.getPermissions().size());//load all permissions
            logger.trace("Permissions for role {} are {}", role.getId(), role.getPermissions());
        }
        return role;
    }

    @Transactional
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public Page<Role> findAllRoles(Pageable pageRequest) {
        Page<Role> rolesPage = roleRepository.findAll(pageRequest);
        return rolesPage;
    }
}
