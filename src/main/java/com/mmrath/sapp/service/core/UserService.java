package com.mmrath.sapp.service.core;

import com.mmrath.sapp.domain.core.Credential;
import com.mmrath.sapp.domain.core.Permission;
import com.mmrath.sapp.domain.core.Role;
import com.mmrath.sapp.domain.core.User;
import com.mmrath.sapp.repository.core.CredentialRepository;
import com.mmrath.sapp.repository.core.UserRepository;
import com.mmrath.sapp.security.SecurityUtils;
import com.mmrath.sapp.service.util.PasswordUtils;
import com.mmrath.sapp.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;


@Component
public class UserService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       CredentialRepository credentialRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    @Transactional
    public User createUser(User user) {
        Assert.isTrue(user.getId() == null || user.getId() == 0L,
                "Invalid argument. User id must not be set");
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(User user) {
        Assert.isTrue(user.getId() != null && user.getId() != 0L,
                "Invalid argument. User id must be set");
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public Long deleteUser(Long userId) {
        userRepository.delete(userId);
        return userId;
    }

    /**
     * This will not return associated data
     *
     * @param userId
     * @return
     */
    @Transactional
    public User findUser(Long userId) {
        User user = userRepository.findOne(userId);
        return user;
    }

    @Transactional
    public Page<User> findUsers(Optional<Specification<User>> spec, Pageable pageable) {
        if (spec.isPresent()) {
            return userRepository.findAll(spec.get(), pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String loginId) {
        Optional<User> optionalUser = userRepository.findOneByUsername(loginId);
        return optionalUser;
    }


    @Transactional(readOnly = true)
    public List<Role> findUserRoles(Long userId) {
        List<Role> roles = userRepository.findOne(userId).getRoles();
        return roles;
    }


    @Transactional(readOnly = true)
    public List<Permission> findAllPermissions(Long userId) {
        List<Permission> userPermissions = new ArrayList<>();
        User user = userRepository.getOne(userId);
        if (user == null) {
            return userPermissions;
        }
        for (Role role : user.getRoles()) {
            userPermissions.addAll(role.getPermissions());
        }
        return userPermissions;
    }

    @Transactional(readOnly = true)
    public Credential findUserCredential(Long userId) {
        Credential credential = credentialRepository.findOne(userId);
        return credential;
    }

    @Transactional
    public User createUserInformation(String username, String password, String firstName, String lastName, String email,
                                      String langKey) {
        User newUser = new User();

        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        if (!hasText(langKey)) {
            langKey = "en"; //Default
        }
        newUser.setLangKey(langKey);
        newUser.setEnabled(true);
        Credential credential = new Credential();
        credential.setPassword(password);
        newUser.setCredential(credential);
        credential.setUser(newUser);
        registerInternal(newUser);
        log.debug("Created information for User: {}", newUser);
        return newUser;
    }

    @Transactional
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return credentialRepository.findOneByActivationKey(key)
                .map(credential -> {
                    // activate given user for the registration key.
                    credential.setActivated(true);
                    credential.setActivationKey(null);
                    credentialRepository.save(credential);
                    log.debug("Activated user with activation key: {}", key);
                    return credential.getUser();
                });
    }

    private User registerInternal(User user) {
        if (user.getCredential() != null && hasText(user.getCredential().getPassword())) {
            Credential credential = user.getCredential();
            String salt = PasswordUtils.generateSalt();
            String password = credential.getPassword();
            credential.setSalt(salt);
            String encodedPassword = PasswordUtils.encodePassword(password, salt);
            credential.setPassword(encodedPassword);
            credential.setLocked(false);
            credential.setExpiryDate(null);
            credential.setInvalidAttempts(0);
            credential.setActivationKey(RandomUtil.generateActivationKey());
            credential.setActivated(false);
        }
        user = userRepository.save(user);
        credentialRepository.save(user.credential);
        return user;
    }

    @Transactional
    public Optional<User> changePassword(String password) {
        return userRepository.findOneByUsername(SecurityUtils.getCurrentLoggedInUsername())
                .map(user -> {
                    Credential credential = user.getCredential();
                    String encodedPassword = PasswordUtils.encodePassword(password, credential.getSalt());
                    credential.setPassword(encodedPassword);
                    credentialRepository.save(credential);
                    return user;
                });
    }

    @Transactional
    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
                .filter(user -> user.getCredential().getActivated())
                .map(user -> {
                    Credential credential = user.getCredential();
                    credential.setResetKey(RandomUtil.generateResetKey());
                    credential.setResetDate(ZonedDateTime.now());
                    credentialRepository.save(credential);
                    return user;
                });
    }

    @Transactional
    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return credentialRepository.findOneByResetKey(key)
                .filter(user -> {
                    ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                    return user.getResetDate().isAfter(oneDayAgo);
                })
                .map(credential -> {
                    String salt = PasswordUtils.generateSalt();
                    credential.setSalt(salt);
                    String encodedPassword = PasswordUtils.encodePassword(newPassword, salt);
                    credential.setPassword(encodedPassword);
                    credential.setExpiryDate(null);
                    credential.setInvalidAttempts(0);
                    credential.setResetKey(null);
                    credential.setResetDate(null);
                    credential.setActivationKey(null);
                    credentialRepository.save(credential);
                    return credential.getUser();
                });
    }

    @Transactional(readOnly = true)
    public User getLoggedInUserWithRole() {
        User user = userRepository.findOneByUsername(SecurityUtils.getCurrentLoggedInUsername()).get();
        user.getRoles().size(); // eagerly load the association
        return user;
    }
}
