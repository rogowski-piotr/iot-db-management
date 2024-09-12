package com.iotdbmanagement.core;

import com.iotdbmanagement.core.dto.user.LoginUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.iotdbmanagement.core.configuration.auth.CustomAuthenticationProvider;
import com.iotdbmanagement.core.service.RoleService;
import com.iotdbmanagement.core.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CoreServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    public void givenLoginUserRequestDtoObject_whenPostLogin_thenReturnStatus200() throws Exception {
//        LoginUserRequest requestObject = new LoginUserRequest();
//        requestObject.setName("admin");
//        requestObject.setPassword("admin");
//
//        ResponseEntity<String> result = template
//                .postForEntity("/login", requestObject, String.class);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
