package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.User;
import ua.tour.api.exceptions.UserRegistrationFailedException;
import ua.tour.api.services.UserService;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
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

    // метод POST, /api/auth/sign-up. Використовується для реєстрації нових користувачів, приймає дані у тілі запиту у форматі JSON (об'єкт класу User)
    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody User user) throws UserRegistrationFailedException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
    }

    // метод POST, /api/auth/is-registered. Використовується для перевірки чи користувач існує за допомогою імені, ім'я передається у тілі запиту у форматі JSON
    @PostMapping("/is-registered")
    public Map<String, Boolean> checkUsername(@RequestBody Map<String, String> payload) {
        return Collections.singletonMap("isRegistered", userService.isRegistered(payload.get("username")));
    }

    // метод POST, /api/auth/is-email-exist. Використовується для перевірки чи емейл користувача існує, емейл передається у тілі запиту у форматі JSON
    @PostMapping("/is-email-exist")
    public Map<String, Boolean> checkEmail(@RequestBody Map<String, String> payload) {
        return Collections.singletonMap("isExist", userService.isEmailExist(payload.get("email")));
    }

    // метод POST, /api/auth/change-password. Метод для зміни паролю, приймає у тілі запиту старий і новий пароль у форматі JSON
    @PostMapping("/change-password")
    public void changePassword(@RequestBody Map<String, String> passwordData) throws AccessDeniedException {
        userService.changePassword(
                passwordData.get("oldPassword"),
                passwordEncoder.encode(passwordData.get("newPassword")));
    }

    // метод POST, /api/auth/update-user-data. Метод для оновлення даних облікового запису користувача, приймає набір даних у тілі запиту, у форматі JSON
    @PostMapping("/update-user-data")
    public void updateUserData(@RequestBody Map<String, String> newUserData) {
        userService.updateUserData(
                newUserData.get("username"),
                newUserData.get("email"),
                newUserData.get("fullName"),
                newUserData.get("mobileNumber"));
    }

    // метод GET, /api/auth/user-data. Метод дозволяє отримати дані облікового запису користувача
    @GetMapping("/user-data")
    public User getUserData(Principal principal) {
        return (User) userService.loadUserByUsername(principal.getName());
    }
}
