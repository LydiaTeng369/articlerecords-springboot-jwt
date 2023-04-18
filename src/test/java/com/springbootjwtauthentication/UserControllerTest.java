package com.springbootjwtauthentication;
import com.springbootjwtauthentication.entity.User;
import com.springbootjwtauthentication.repository.UserRepository;
import com.springbootjwtauthentication.request.LoginForm;
import com.springbootjwtauthentication.request.SignUpForm;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    private String baseUrl;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("rtyu");
        user.setPassword("123456");
        user.setName("zzc");
        user.setEmail("zzc@163.com");
        baseUrl = "http://localhost:" + port + "/api/user";
        userRepository.save(user);
    }

    @After
    public void tearDown() {
        userRepository.delete(user);
    }

    @Test
    public void testLoginSuccess() {
        LoginForm loginUser = new LoginForm();
        loginUser.setUsername("newuser");
        loginUser.setPassword("123456");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/login", loginUser, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLoginFailureWrongPassword() {
        LoginForm loginUser = new LoginForm();
        loginUser.setUsername("newuser");
        loginUser.setPassword("wrongpassword");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/login", loginUser, String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testLoginFailureWrongUsername() {
        LoginForm loginUser = new LoginForm();
        loginUser.setUsername("newuser123");
        loginUser.setPassword("123456");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/login", loginUser, String.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testRegistrationSuccess() {
        SignUpForm newUser = new SignUpForm();
        newUser.setUsername("newuser");
        newUser.setPassword("123456");
        newUser.setConfirmPassword("123456");
        newUser.setEmail("asd@163.com");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "newuser");
        map.add("password", "123456");
        map.add("email", "asd@163.com");
        map.add("confirmPassword", "123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", request, String.class);
/*        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());*/
        Optional<User> userOptional = userRepository.findByUsername("newuser");
        User user = userOptional.get();
        Assert.assertNotNull(user);
        Assert.assertEquals("newuser", user.getUsername());
    }

    @Test
    public void testRegistrationFailurePasswordsDoNotMatch() {
        SignUpForm newUser = new SignUpForm();
        newUser.setUsername("newuser12");
        newUser.setPassword("123456");
        newUser.setConfirmPassword("123457"); // change to different password
        newUser.setEmail("asd@163.com");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "newuser12");
        map.add("password", "123456");
        map.add("email", "asd@163.com");
        map.add("confirmPassword", "123457"); // change to different password
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // should return 400 Bad Request

        Optional<User> userOptional = userRepository.findByUsername("newuser12");
        Assert.assertFalse(userOptional.isPresent()); // user should not be created
    }

    @Test
    public void testRegistrationFailureUsernameAlreadyExists() {
        SignUpForm newUser = new SignUpForm();
        newUser.setUsername("newuser");// username already exists
        newUser.setPassword("123456");
        newUser.setConfirmPassword("123456");
        newUser.setEmail("asd@163.com");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "newuser");// username already exists
        map.add("password", "123456");
        map.add("email", "asd@163.com");
        map.add("confirmPassword", "123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", request, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // should return 400 Bad Request

        Optional<User> userOptional = userRepository.findByUsername("newuser");

    }

}
