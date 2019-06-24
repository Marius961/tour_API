package ua.tour.api.controllers;

import org.springframework.data.domain.Page;
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

    // метод GET, /api/tours. Використовується для отримання списку всіх турів, номер сторінки передається як URL параметр
    @GetMapping
    public Page<Tour> getAllTours(@RequestParam(name = "p") int page) {
        return tourService.getAllTours(page);
    }

    // метод DELETE, /api/tours/{id}. Використовується для видалення туру, id туру, який потрібно видалити, передається як path параметр у URL
    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) throws ReservationsExistException, DeletionException, FileNotFoundException {
        tourService.removeTour(id);
    }

    // метод PUT, /api/tours. Використовується для оновлення даних туру, об'єкт з новими даними передається у тілі запиту у форматі JSON
    @PutMapping
    public void updateTour(@Valid @RequestBody Tour tour) throws NotFoundException {
        tourService.updateTour(tour);
    }

    // метод POST, /api/tours. Використовуєть для додавання туру, сформований об'єкт передається у тілі запиту у форматі JSON, також у тілі запиту передається файл (фото) туру
    @PostMapping
    public void createTour(
            @Valid @RequestPart(name = "tour") Tour tour,
            @RequestPart(name = "image") MultipartFile file) throws NotFoundException, IOException {
        tourService.createNewTour(tour, file);
    }
}
