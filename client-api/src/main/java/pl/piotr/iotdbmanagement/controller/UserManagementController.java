package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotr.iotdbmanagement.dto.user.GetRolesResponse;
import pl.piotr.iotdbmanagement.dto.user.GetUserResponse;
import pl.piotr.iotdbmanagement.dto.user.GetUsersResponse;
import pl.piotr.iotdbmanagement.dto.user.UpdateUserRequest;
import pl.piotr.iotdbmanagement.role.Role;
import pl.piotr.iotdbmanagement.service.RoleService;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
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

    @GetMapping("/roles")
    public ResponseEntity<Iterable<GetRolesResponse.Role>> getAllRoles() {
        logger.info("GET all roles");
        List<Role> resultList = roleService.getAllRoles();
        return resultList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetRolesResponse.entityToDtoMapper().apply(resultList));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserRequest request, @PathVariable("id") Long id) {
        logger.info("UPDATE");
        Optional<User> userOptional = userService.findById(id);
        Optional<Role> roleOptional = roleService.findRoleById(request.getRole());
        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User updatedUser = UpdateUserRequest.dtoToEntityUpdater()
                    .apply(userOptional.get(), roleOptional.get());
            userService.update(updatedUser);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        logger.info(MessageFormat.format("DELETE user, id: {0}", id));
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
