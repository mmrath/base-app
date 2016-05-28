package com.mmrath.sapp.repository.core;

import com.mmrath.sapp.domain.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByEmail(String email);

}
