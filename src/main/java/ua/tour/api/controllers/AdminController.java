package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tour.api.entities.User;
import ua.tour.api.services.UserService;

@RestController
@RequestMapping("/users/")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> allUsers() {
        return userService.getAllUsers();
    }
}
