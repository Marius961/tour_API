package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.exceptions.SeatsCountOverflowException;
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
    public void createReservation(@Valid @RequestBody TourReservation reservation, Principal principal) throws NotFoundException, SeatsCountOverflowException {
        reservationService.addReservation(reservation, principal);
    }

    @GetMapping
    public Iterable<TourReservation> getAllUserReservations(Principal principal) {
        return reservationService.getAllUserReservations(principal);
    }

    @PostMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable Long id) throws NotFoundException {
        reservationService.cancelReservation(id);
    }

}
