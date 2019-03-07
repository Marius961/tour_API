package ua.tour.api.controllers;

import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.exceptions.ReservationsExistException;
import ua.tour.api.services.TourService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public Iterable<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) throws ReservationsExistException, DeletionException {
        tourService.removeTour(id);
    }


    @PutMapping
    public void updateTour(@Valid @RequestBody Tour tour) throws NotFoundException {
        tourService.updateTour(tour);
    }


    @PostMapping
    public void createTour(@Valid @RequestBody Tour tour) throws NotFoundException {
        tourService.createNewTour(tour);
    }
}
