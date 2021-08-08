package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.user.User;
import pl.piotr.iotdbmanagement.user.UserRepository;

@Service
public class AuthService {
    private UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

}
