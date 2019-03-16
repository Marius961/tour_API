package ua.tour.api.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.exceptions.ReservationsExistException;
import ua.tour.api.services.TourService;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    public void deleteTour(@PathVariable Long id) throws ReservationsExistException, DeletionException, FileNotFoundException {
        tourService.removeTour(id);
    }


    @PutMapping
    public void updateTour(@Valid @RequestBody Tour tour) throws NotFoundException {
        tourService.updateTour(tour);
    }


    @PostMapping
    public void createTour(
            @Valid @RequestPart(name = "tour") Tour tour,
            @RequestPart(name = "image") MultipartFile file) throws NotFoundException, IOException {
        tourService.createNewTour(tour, file);
    }
}
