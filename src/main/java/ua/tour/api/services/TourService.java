package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.repo.TourRepository;
import ua.tour.api.repo.TourReservationRepository;

import java.util.Optional;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourReservationRepository reservationRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourReservationRepository tourReservationRepository) {
        this.tourRepository = tourRepository;
        this.reservationRepository = tourReservationRepository;
    }

    public void createNewTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void removeTour(Long tourId) throws Exception {
        Optional<Tour> opTour = tourRepository.findById(tourId);
        if (opTour.isPresent()) {
            Long reservationsCount = reservationRepository.countByTour(opTour.get());
            if (reservationsCount == 0) {
                tourRepository.deleteById(tourId);
            } else throw new Exception();
        } else throw new Exception();
    }


    public Iterable<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public void updateTour(Tour tour) throws Exception {
        if (tourRepository.existsById(tour.getId())) {
            tourRepository.save(tour);
        } else throw new Exception();
    }
}
