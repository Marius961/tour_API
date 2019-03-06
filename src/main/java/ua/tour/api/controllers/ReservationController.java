package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.services.ReservationService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public void createReservation(@Valid @RequestBody TourReservation reservation, Principal principal) throws Exception {
        reservationService.addReservation(reservation, principal);
    }

    @GetMapping
    public Iterable<TourReservation> getAllUserReservations(Principal principal) throws Exception {
        return reservationService.getAllUserReservations(principal);
    }

    @PostMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable Long id) throws Exception {
        reservationService.cancelReservation(id);
    }

}
