package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotr.iotdbmanagement.dto.user.GetUserResponse;
import pl.piotr.iotdbmanagement.dto.user.GetUsersResponse;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.service.RoleService;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api_auth/users")
public class UserManagementController {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserManagementController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Iterable<GetUsersResponse.User>> GetUsers() {
        logger.info("GET users");
        List<User> resultList = userService.findAll();
        return resultList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetUsersResponse.entityToDtoMapper().apply(resultList));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getSingleUser(@PathVariable(name = "id") Long id) {
        logger.info("GET single user, id: " + id);
        Optional<User> userOptional = userService.findById(id);
        return userOptional
                .map(user -> ResponseEntity.ok(GetUserResponse.entityToDtoMapper().apply(user)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
