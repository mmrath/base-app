package com.mmrath.sapp.repository.core;

import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(name = Role.QUERY_GET_UNASSIGNED_PERMISSIONS)
    List<Permission> findAllUnassignedPermissions(@Param("id") Long id);

}
