package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.entities.User;
import ua.tour.api.services.ReservationService;
import ua.tour.api.services.UserService;


@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, ReservationService reservationService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.passwordEncoder = passwordEncoder;
    }

    // метод GET, /api/admin/all-users. Використовується для отримання списку всіх користувачів, номер сторінки передається у URL як параметр
    @GetMapping("all-users")
    public Iterable<User> allUsers(@RequestParam(name = "p") int page) {
        return userService.getAllUsers(page);
    }

    // метод POST, /api/admin//users/set-admin/{id}. Використовується для надання прав адміністратора користувачеві, id користувача передається як path параметр
    @PostMapping("/users/set-admin/{id}")
    public void setAdmin(@PathVariable Long id) {
        userService.addAdminRole(id);
    }

    // метод POST, /api/admin/. Використовується для відкликання прав адміністратора у користувача, id користувача передається як URL параметр
    @PostMapping("/users/remove-admin/{id}")
    public void removeAdmin(@PathVariable Long id) {
        userService.removeAdminRole(id);
    }

    // метод GET, /api/admin//all-reservations. Використовується для отримання всіх резервувань користувачів, номер сторінки передається як URL параметр
    @GetMapping("/all-reservations")
    public Iterable<TourReservation> getAllReservations(@RequestParam(name = "p") int page) {
        return reservationService.getAllReservations(page);
    }
}
