package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.repo.TourRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void createNewTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void removeTour(Long tourId) {
        tourRepository.deleteById(tourId);
    }


    public Iterable<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public void updateTour(Tour tour) {
        if (tourRepository.existsById(tour.getId())) {
            tourRepository.save(tour);
        }
    }
}
