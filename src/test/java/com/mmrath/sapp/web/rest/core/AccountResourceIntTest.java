package com.mmrath.sapp.web.rest.core;


import com.mmrath.sapp.domain.core.Role;
import com.mmrath.sapp.domain.core.User;
import com.mmrath.sapp.repository.core.UserRepository;
import com.mmrath.sapp.service.core.MailService;
import com.mmrath.sapp.service.core.UserService;
import com.mmrath.sapp.web.dto.ManagedUserDto;
import com.mmrath.sapp.web.dto.UserDto;
import com.mmrath.sapp.web.rest.AbstractWebIntegrationTest;
import com.mmrath.sapp.web.rest.TestUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see UserService
 */

public class AccountResourceIntTest extends AbstractWebIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Mock
    private UserService mockUserService;

    @Mock
    private MailService mockMailService;

    private MockMvc restUserMockMvc;

    private MockMvc restMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mockMailService).sendActivationEmail((User) anyObject(), anyString());

        AccountResource accountResource = new AccountResource();
        ReflectionTestUtils.setField(accountResource, "userRepository", userRepository);
        ReflectionTestUtils.setField(accountResource, "userService", userService);
        ReflectionTestUtils.setField(accountResource, "mailService", mockMailService);

        AccountResource accountUserMockResource = new AccountResource();
        ReflectionTestUtils.setField(accountUserMockResource, "userRepository", userRepository);
        ReflectionTestUtils.setField(accountUserMockResource, "userService", mockUserService);
        ReflectionTestUtils.setField(accountUserMockResource, "mailService", mockMailService);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build();
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/authenticate")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Ignore //FIXIT to test return of logged in user
    @Test
    public void testAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/authenticate")
                .with(request -> {
                    request.setRemoteUser("test");
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {


        User user = new User();
        user.setUsername("test");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john.doe@jhipter.com");

        Role role = new Role();
        role.setName("CUSTOMER");
        role.setDescription("TEST");
        role.setId(1L);

        user.setRoles(Arrays.asList(role));

        when(mockUserService.getLoggedInUserWithRole()).thenReturn(user);

        restUserMockMvc.perform(get("/api/account")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.login").value("test"))
                .andExpect(jsonPath("$.firstName").value("john"))
                .andExpect(jsonPath("$.lastName").value("doe"))
                .andExpect(jsonPath("$.email").value("john.doe@jhipter.com"));
    }

    @Test
    public void testGetUnknownAccount() throws Exception {
        when(mockUserService.getLoggedInUserWithRole()).thenReturn(null);

        restUserMockMvc.perform(get("/api/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    public void testRegisterValidWithEmailAsLogin() throws Exception {
        ManagedUserDto validUser = new ManagedUserDto(
                null,                   // id
                "joe3@test.com",                  // login
                "password",             // password
                "Joe",                  // firstName
                "Shmoe",                // lastName
                "joe@example.com",      // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated());

        Optional<User> user = userRepository.findOneByUsername("joe3@test.com");
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    @Transactional
    public void testRegisterValidWithUsernameAsLogin() throws Exception {
        ManagedUserDto validUser = new ManagedUserDto(
                null,                   // id
                "joe3",                  // login
                "password",             // password
                "Joe",                  // firstName
                "Shmoe",                // lastName
                "joe@example.com",      // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated());

        Optional<User> user = userRepository.findOneByUsername("joe3");
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    @Transactional
    public void testRegisterInvalidLogin() throws Exception {
        ManagedUserDto invalidUser = new ManagedUserDto(
                null,                   // id
                "funky-log!n",          // login <-- invalid
                "password",             // password
                "Funky",                // firstName
                "One",                  // lastName
                "funky@example.com",    // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        restUserMockMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("funky@example.com");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void testRegisterInvalidEmail() throws Exception {
        ManagedUserDto invalidUser = new ManagedUserDto(
                null,                   // id
                "bob",              // login
                "password",         // password
                "Bob",              // firstName
                "Green",            // lastName
                "invalid",          // e-mail <-- invalid
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        restUserMockMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByUsername("bob");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void testRegisterInvalidPassword() throws Exception {
        ManagedUserDto invalidUser = new ManagedUserDto(
                null,                   // id
                "bob",              // login
                "123",              // password with only 3 digits
                "Bob",              // firstName
                "Green",            // lastName
                "bob@example.com",  // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        restUserMockMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByUsername("bob");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void testRegisterDuplicateLogin() throws Exception {
        // Good
        ManagedUserDto validUser = new ManagedUserDto(
                null,                   // id
                "alice",                // login
                "password",             // password
                "Alice",                // firstName
                "Something",            // lastName
                "alice@example.com",    // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        // Duplicate login, different e-mail
        ManagedUserDto duplicatedUser = new ManagedUserDto(validUser.getId(), validUser.getLogin(), validUser.getPassword(), validUser.getLogin(), validUser.getLastName(),
                "alicejr@example.com", validUser.getLangKey(), validUser.getCreatedDate(), validUser.getLastModifiedBy(), validUser.getLastModifiedDate());

        // Good user
        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated());

        // Duplicate login
        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
                .andExpect(status().is4xxClientError());

        Optional<User> userDup = userRepository.findOneByEmail("alicejr@example.com");
        assertThat(userDup.isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void testRegisterDuplicateEmail() throws Exception {
        // Good
        ManagedUserDto validUser = new ManagedUserDto(
                null,                   // id
                "john",                 // login
                "password",             // password
                "John",                 // firstName
                "Doe",                  // lastName
                "john@example.com",     // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        // Duplicate e-mail, different login
        ManagedUserDto duplicatedUser = new ManagedUserDto(validUser.getId(), "johnjr", validUser.getPassword(), validUser.getLogin(), validUser.getLastName(),
                validUser.getEmail(), validUser.getLangKey(), validUser.getCreatedDate(), validUser.getLastModifiedBy(), validUser.getLastModifiedDate());

        // Good user
        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andExpect(status().isCreated());

        // Duplicate e-mail
        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
                .andExpect(status().is4xxClientError());

        Optional<User> userDup = userRepository.findOneByUsername("johnjr");
        assertThat(userDup.isPresent()).isFalse();
    }

    @Test
    @Transactional
    public void testRegisterAdminIsIgnored() throws Exception {
        ManagedUserDto validUser = new ManagedUserDto(
                null,                   // id
                "badguy",               // login
                "password",             // password
                "Bad",                  // firstName
                "Guy",                  // lastName
                "badguy@example.com",   // e-mail
                "en",               // langKey
                null,                   // createdDate
                null,                   // lastModifiedBy
                null                    // lastModifiedDate
        );

        restMvc.perform(
                post("/api/register")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(validUser)))
                .andDo(print())
                .andExpect(status().isCreated());

        Optional<User> userDup = userRepository.findOneByUsername("badguy");
        assertThat(userDup.isPresent()).isTrue();
        assertThat(userDup.get().getRoles()).hasSize(0);
    }

    @Test
    @Transactional
    public void testSaveInvalidLogin() throws Exception {
        UserDto invalidUser = new UserDto(
                "funky-log!n",          // login <-- invalid
                "password",
                "Funky",                // firstName
                "One",                  // lastName
                "funky@example.com",    // e-mail
                "en"           // langKey
        );

        restUserMockMvc.perform(
                post("/api/account")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
                .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("funky@example.com");
        assertThat(user.isPresent()).isFalse();
    }
}
