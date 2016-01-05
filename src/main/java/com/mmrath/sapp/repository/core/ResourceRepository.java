package com.mmrath.sapp.repository.core;

import com.mmrath.sapp.domain.core.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by murali on 5/01/2016.
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
