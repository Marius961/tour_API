package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AdminController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }


    @GetMapping("all-users")
    public Iterable<User> allUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users/set-admin/{id}")
    public void setAdmin(@PathVariable Long id) {
        userService.addAdminRole(id);
    }

    @PostMapping("/users/remove-admin/{id}")
    public void removeAdmin(@PathVariable Long id) {
        userService.removeAdminRole(id);
    }

    @GetMapping("/all-reservations")
    public Iterable<TourReservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
