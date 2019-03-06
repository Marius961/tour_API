package ua.tour.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.Tour;
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
    public void deleteTour(@PathVariable Long id) {
        tourService.removeTour(id);
    }


    @PutMapping
    public void updateTour(@Valid @RequestBody Tour tour) {
        tourService.updateTour(tour);
    }


    @PostMapping
    public void createTour(@Valid @RequestBody Tour tour) {
        tourService.createNewTour(tour);
    }
}
