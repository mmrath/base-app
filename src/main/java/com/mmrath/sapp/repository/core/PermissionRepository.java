package com.mmrath.sapp.repository.core;

import com.mmrath.sapp.domain.core.AccessLevel;
import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select distinct p.resource from Permission p")
    List<Resource> findDistinctResources();

    @Query("select p.accessLevel from Permission p where p.resource =:resource")
    List<AccessLevel> findAccessLevelsForResource(Resource resource);
}
