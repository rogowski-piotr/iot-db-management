package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.piotr.iotdbmanagement.dto.user.RegisterUserRequest;
import pl.piotr.iotdbmanagement.service.RoleService;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import javax.validation.Valid;

import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("api/users")
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
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
