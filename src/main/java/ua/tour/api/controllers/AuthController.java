package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tour.api.entities.User;
import ua.tour.api.exceptions.UserRegistrationFailedException;
import ua.tour.api.services.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody User user) throws UserRegistrationFailedException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
    }

    @PostMapping("/is-registered")
    public Map<String, Boolean> checkUsername(@RequestBody Map<String, String> payload) {
        return Collections.singletonMap("isRegistered", userService.isRegistered(payload.get("username")));
    }

    @PostMapping("/is-email-exist")
    public Map<String, Boolean> checkEmail(@RequestBody Map<String, String> payload) {
        return Collections.singletonMap("isExist", userService.isEmailExist(payload.get("email")));
    }
}
