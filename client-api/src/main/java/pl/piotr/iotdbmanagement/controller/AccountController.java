package pl.piotr.iotdbmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotr.iotdbmanagement.dto.user.UpdatePasswordRequest;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api_auth/account")
public class AccountController {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        logger.info("UPDATE-PASSWORD");
        if (! userService.isCredentialsValid(request.getEmail(), request.getCurrentPassword())) {
            logger.info("not valid credentials");
            return ResponseEntity.status(401).build();
        }
        Optional<User> userOptional = userService.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            userService.updatePassword(userOptional.get(), request.getNewPassword());
            logger.info("updated successful");
            return ResponseEntity.accepted().build();
        }
        logger.info("can not update");
        return ResponseEntity.notFound().build();
    }

}
