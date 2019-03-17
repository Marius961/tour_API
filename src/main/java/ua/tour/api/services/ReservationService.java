package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.entities.User;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.exceptions.SeatsCountOverflowException;
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

    public void addReservation(TourReservation reservation, Principal principal) throws SeatsCountOverflowException, NotFoundException {
        Optional<Tour> opTour = tourRepository.findById(reservation.getTour().getId());
        if (opTour.isPresent()) {
            Tour tour = opTour.get();
            Long reservationCount = tourReservationRepository.countByTour(tour);
            if (reservationCount < tour.getSeatCount()) {
                User currentUser = (User) userService.loadUserByUsername(principal.getName());
                if (currentUser != null) {
                    reservation.setId(null);
                    reservation.setActive(true);
                    reservation.setUser(currentUser);
                    tourReservationRepository.save(reservation);
                }
            } else throw new SeatsCountOverflowException("Seats for the selected tour have expired");
        } else throw new NotFoundException("Tour with id: " + reservation.getTour().getId() + " not found");

    }

    public Page<TourReservation> getAllUserReservations(int page, Principal principal) throws UsernameNotFoundException {
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        if (currentUser != null) {
            return tourReservationRepository.findAllByUser(currentUser, PageRequest.of(page, 15));
        } else throw new UsernameNotFoundException("User with username " + principal.getName() + " not found");
    }

    public void cancelReservation(Long reservationId) throws NotFoundException {
        Optional<TourReservation> opReservation = tourReservationRepository.findById(reservationId);
        if (opReservation.isPresent()) {
            TourReservation reservation = opReservation.get();
            reservation.setActive(false);
            tourReservationRepository.save(reservation);
        } else throw new NotFoundException("Reservation with id: " + reservationId + " not found");
    }

    public Page<TourReservation> getAllReservations(int page) {
        return tourReservationRepository.findAll(PageRequest.of(page, 16));
    }
}
