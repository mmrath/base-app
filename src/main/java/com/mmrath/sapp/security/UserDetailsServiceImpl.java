package com.mmrath.sapp.security;

import com.mmrath.sapp.domain.core.User;
import com.mmrath.sapp.service.core.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);
        String lowercaseUsername = username.toLowerCase();
        Optional<User> userFromDatabase = userService.findUserByUsername(lowercaseUsername);
        return userFromDatabase.map(user -> {

            List<GrantedAuthority> grantedAuthorities =
                    userService.findAllPermissions(user.getId()).stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(lowercaseUsername, "",
                    grantedAuthorities);

        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseUsername + " was not found in the " +
                "database"));
    }
}
