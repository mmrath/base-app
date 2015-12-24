package com.mmrath.sapp.repository.core;

import com.mmrath.sapp.domain.core.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Murali on 11/18/13.
 */
public interface UserCredentialRepository extends JpaRepository<Credential, Long> {

}
