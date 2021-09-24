package pl.piotr.iotdbmanagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotr.iotdbmanagement.configuration.auth.CustomAuthenticationProvider;
import pl.piotr.iotdbmanagement.dto.sensor.CreateSensorRequest;
import pl.piotr.iotdbmanagement.dto.user.AuthUserResponse;
import pl.piotr.iotdbmanagement.dto.user.LoginUserRequest;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.RoleService;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ClientApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test.properties")
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
        LoginUserRequest requestObject = new LoginUserRequest();
        requestObject.setName("admin");
        requestObject.setPassword("admin");

        ResponseEntity<String> result = template
                .postForEntity("/login", requestObject, String.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
