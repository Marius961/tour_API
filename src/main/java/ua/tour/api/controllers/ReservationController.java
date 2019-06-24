package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // метод POST, /api/reservations. Використовується для створення нового резервування, об'єкт отримується з тіла запиту у форматі JSON
    @PostMapping
    public void createReservation(@Valid @RequestBody TourReservation reservation, Principal principal) throws NotFoundException, SeatsCountOverflowException {
        reservationService.addReservation(reservation, principal);
    }

    // метод GET, /api/reservations. Використовується для отримання всіх резервувань користувача, номер сторінки для виводу передається як URL параметр
    @GetMapping
    public Page<TourReservation> getAllUserReservations(@RequestParam(name = "p") int page, Principal principal) {
        return reservationService.getAllUserReservations(page, principal);
    }

    // метод POST, /api/reservations/cancel/{id}. Використовується для скасування резервування, id резервування передається як path параметр у URL
    @PostMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable Long id) throws NotFoundException {
        reservationService.cancelReservation(id);
    }

}
