package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.piotr.iotdbmanagement.configuration.auth.CustomAuthenticationProvider;
import pl.piotr.iotdbmanagement.dto.user.AuthUserResponse;
import pl.piotr.iotdbmanagement.dto.user.LoginUserRequest;
import pl.piotr.iotdbmanagement.dto.user.RegisterUserRequest;
import pl.piotr.iotdbmanagement.service.RoleService;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping
public class AuthController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private CustomAuthenticationProvider authenticationProvider;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AuthController(CustomAuthenticationProvider authenticationProvider, UserService userService, RoleService roleService) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthUserResponse> getAvailableTypes(@Valid @RequestBody LoginUserRequest request, UriComponentsBuilder builder) {
        logger.info("Trying login: " + request.toString());
        try {
            if (authenticationProvider.authenticate(LoginUserRequest.dtoToAuthMapper().apply(request)).isAuthenticated()) {
                User user = userService.findUserByEmail(request.getName());
                return ResponseEntity.ok(AuthUserResponse.entityToDtoMapper().apply(user));
            }
        } catch (Exception ignore) {}
        return ResponseEntity.status(401).build();
    }

    @PostMapping("register")
    public ResponseEntity createUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder builder) {
        logger.info("CREATE USER: " + request);
        if (userService.findUserByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("User with the given address already exists");
        }
        User user = RegisterUserRequest.dtoToEntityMapper().apply(request);
        user.setRole(roleService.getRoleForUser());
        userService.save(user);
        return ResponseEntity.created(builder.pathSegment("api", "sensors").build().toUri()).build();
    }

}
