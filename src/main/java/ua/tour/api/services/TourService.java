package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.repo.TourRepository;

import java.util.Date;

@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void createNewTour(Tour tour) {
        tour.setStartDate(new Date(tour.getStartDate().getTime()*1000));
        tour.setEndDate(new Date(tour.getEndDate().getTime()*1000));
        tourRepository.save(tour);
    }

    public Iterable<Tour> getAllTours() {
        return tourRepository.findAll();
    }
}
