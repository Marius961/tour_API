package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.User;
import ua.tour.api.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }



    /* testing user registration JSON
    {"id":5,"username":"username","email":"email@gmail.com","password":"pass123","mobileNumber":"0346464","fullName":"User Test"}
     */
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userService.createUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public @ResponseBody Iterable<User> getUsers() {
        return userService.getAllUsers();
    }
}
