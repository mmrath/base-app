package com.mmrath.sapp.repository.core;

import com.codahale.metrics.Slf4jReporter;
import com.mmrath.sapp.domain.core.Credential;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Murali on 11/18/13.
 */
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findOneByActivationKey(String key);

    Optional<Credential>  findOneByResetKey(String key);
}
