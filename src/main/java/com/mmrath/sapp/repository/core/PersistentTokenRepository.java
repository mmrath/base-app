package com.mmrath.sapp.repository.core;

import com.mmrath.sapp.domain.core.PersistentToken;
import com.mmrath.sapp.domain.core.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Created by murali on 19/01/15.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(Instant localDate);

}

