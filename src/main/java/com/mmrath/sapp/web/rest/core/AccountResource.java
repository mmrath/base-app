package com.mmrath.sapp.web.rest.core;

import com.codahale.metrics.annotation.Timed;
import com.mmrath.sapp.domain.core.User;
import com.mmrath.sapp.repository.core.UserRepository;
import com.mmrath.sapp.security.SecurityUtils;
import com.mmrath.sapp.service.core.MailService;
import com.mmrath.sapp.service.core.UserService;
import com.mmrath.sapp.web.dto.KeyAndPasswordDto;
import com.mmrath.sapp.web.dto.ManagedUserDto;
import com.mmrath.sapp.web.dto.UserDto;
import com.mmrath.sapp.web.errors.FieldErrorDto;
import com.mmrath.sapp.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {


    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * POST  /register : register the user.
     *
     * @param managedUserDto the managed user DTO
     * @param request        the HTTP request
     * @return the ResponseEntity with status 201 (Created) if the user is registred or 400 (Bad Request) if the login or e-mail is already in use
     */
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody ManagedUserDto managedUserDto, HttpServletRequest request) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        return userRepository.findOneByEmail(managedUserDto.getEmail().toLowerCase())
                .map(user -> new ResponseEntity<>(new FieldErrorDto("email", "Email address already registered"), HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByUsername(managedUserDto.getLogin())
                        .map(user -> new ResponseEntity<>(new FieldErrorDto("login", "Login not available"), HttpStatus.BAD_REQUEST))
                        .orElseGet(() -> {
                            User user = userService.createUserInformation(managedUserDto.getLogin(), managedUserDto.getPassword(),
                                    managedUserDto.getFirstName(), managedUserDto.getLastName(), managedUserDto.getEmail().toLowerCase(),
                                    managedUserDto.getLangKey());
                            String baseUrl = request.getScheme() + // "http"
                                    "://" +                                // "://"
                                    request.getServerName() +              // "myhost"
                                    ":" +                                  // ":"
                                    request.getServerPort() +              // "80"
                                    request.getContextPath();              // "/myContextPath" or "" if deployed in root context

                            mailService.sendActivationEmail(user, baseUrl);
                            return new ResponseEntity<>(HttpStatus.CREATED);
                        })
                );
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @RequestMapping(value = "/activate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
                .map(credential -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @RequestMapping(value = "/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public User isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return userService.getLoggedInUserWithRole();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDto> getAccount() {
        return Optional.ofNullable(userService.getLoggedInUserWithRole())
                .map(user -> new ResponseEntity<>(new UserDto(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDto the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @RequestMapping(value = "/account",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@Valid @RequestBody UserDto userDto) {
        Optional<User> existingUser = userRepository.findOneByEmail(userDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getUsername().equalsIgnoreCase(userDto.getLogin()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        return userRepository
                .findOneByUsername(SecurityUtils.getCurrentLoggedInUsername())
                .map(user -> {
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    user.setEmail(userDto.getEmail());
                    user.setLangKey(userDto.getLangKey());
                    userRepository.save(user);
                    return new ResponseEntity<String>(HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @RequestMapping(value = "/account/change_password",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST   /account/reset_password/init : Send an e-mail to reset the password of the user
     *
     * @param mail    the mail of the user
     * @param request the HTTP request
     * @return the ResponseEntity with status 200 (OK) if the e-mail was sent, or status 400 (Bad Request) if the e-mail address is not registred
     */
    @RequestMapping(value = "/account/reset_password/init",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
        return userService.requestPasswordReset(mail)
                .map(user -> {
                    String baseUrl = request.getScheme() +
                            "://" +
                            request.getServerName() +
                            ":" +
                            request.getServerPort() +
                            request.getContextPath();
                    mailService.sendPasswordResetMail(user, baseUrl);
                    return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
                }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @RequestMapping(value = "/account/reset_password/finish",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordDto keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
                .map(user -> new ResponseEntity<String>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
                password.length() >= ManagedUserDto.PASSWORD_MIN_LENGTH &&
                password.length() <= ManagedUserDto.PASSWORD_MAX_LENGTH);
    }
}
