package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.entities.User;
import ua.tour.api.repo.TourRepository;
import ua.tour.api.repo.TourReservationRepository;

import java.security.Principal;
import java.util.Optional;

@Service
public class ReservationService {

    private TourReservationRepository tourReservationRepository;
    private TourRepository tourRepository;
    private UserService userService;

    @Autowired
    public ReservationService(
            TourReservationRepository tourReservationRepository,
            UserService userService,
            TourRepository tourRepository) {

        this.tourReservationRepository = tourReservationRepository;
        this.userService = userService;
        this.tourRepository = tourRepository;
    }

    public void addReservation(TourReservation reservation, Principal principal) throws Exception {
        Optional<Tour> opTour = tourRepository.findById(reservation.getTour().getId());
        if (opTour.isPresent()) {
            Tour tour = opTour.get();
            Long reservationCount = tourReservationRepository.countByTour(tour);
            if (reservationCount < tour.getSeatCount()) {
                User currentUser = (User) userService.loadUserByUsername(principal.getName());
                if (currentUser != null) {
                    reservation.setActive(true);
                    reservation.setUser(currentUser);
                    tourReservationRepository.save(reservation);
                }
            } else throw new Exception();
        } else throw new Exception();

    }

    public Iterable<TourReservation> getAllUserReservations(Principal principal) throws Exception {
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        if (currentUser != null) {
            return tourReservationRepository.findAllByUser(currentUser);
        } else throw new Exception();
    }

    public void cancelReservation(Long reservationId) throws Exception {
        Optional<TourReservation> opReservation = tourReservationRepository.findById(reservationId);
        if (opReservation.isPresent()) {
            TourReservation reservation = opReservation.get();
            reservation.setActive(false);
            tourReservationRepository.save(reservation);
        } else throw new Exception();
    }
}
