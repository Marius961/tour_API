package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.*;
import ua.tour.api.repo.HotelRepository;
import ua.tour.api.repo.TourRepository;
import ua.tour.api.repo.TourReservationRepository;

import java.util.Optional;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourReservationRepository tourReservationRepository, HotelRepository hotelRepository) {
        this.tourRepository = tourRepository;
        this.reservationRepository = tourReservationRepository;
        this.hotelRepository = hotelRepository;
    }

    public void createNewTour(Tour tour) throws NotFoundException {
        boolean isHotelExist = hotelRepository.existsById(tour.getHotel().getId());
        if (isHotelExist) {
            tourRepository.save(tour);
        } else throw new NotFoundException("Tour can not be created because hotel with id: " + tour.getHotel().getId() + " not exist.");
    }

    public void removeTour(Long tourId) throws ReservationsExistException, DeletionException {
        Optional<Tour> opTour = tourRepository.findById(tourId);
        if (opTour.isPresent()) {
            Long reservationsCount = reservationRepository.countByTour(opTour.get());
            if (reservationsCount == 0) {
                tourRepository.deleteById(tourId);
            } else throw new ReservationsExistException("The tour can not be deleted because reservations have been found.");
        } else throw new DeletionException("Unable to delete tour, because it not exist");
    }


    public Iterable<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public void updateTour(Tour tour) throws NotFoundException {
        if (tourRepository.existsById(tour.getId())) {
            tourRepository.save(tour);
        } else throw new NotFoundException("Can not update tour, because it does not exist");
    }
}
