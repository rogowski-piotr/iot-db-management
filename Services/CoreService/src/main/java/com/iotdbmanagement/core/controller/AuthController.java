package com.iotdbmanagement.core.controller;

import com.iotdbmanagement.core.configuration.auth.CustomAuthenticationProvider;
import com.iotdbmanagement.core.dto.user.AuthUserResponse;
import com.iotdbmanagement.core.dto.user.LoginUserRequest;
import com.iotdbmanagement.core.dto.user.RegisterUserRequest;
import com.iotdbmanagement.core.service.RoleService;
import com.iotdbmanagement.core.service.UserService;
import com.iotdbmanagement.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<AuthUserResponse> login(@Valid @RequestBody LoginUserRequest request, UriComponentsBuilder builder) {
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
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder builder) {
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
