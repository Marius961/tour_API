package ua.tour.api.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.User;
import ua.tour.api.repo.UserRepository;
import ua.tour.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
    }

    @GetMapping("/all")
    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }
}
