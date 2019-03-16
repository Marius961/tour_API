package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.*;
import ua.tour.api.repo.HotelRepository;
import ua.tour.api.repo.TourRepository;
import ua.tour.api.repo.TourReservationRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final ImageService imageService;

    @Autowired
    public TourService(TourRepository tourRepository, TourReservationRepository reservationRepository, HotelRepository hotelRepository, ImageService imageService) {
        this.tourRepository = tourRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.imageService = imageService;
    }

    public void createNewTour(Tour tour, MultipartFile file) throws NotFoundException, IOException {
        boolean isHotelExist = hotelRepository.existsById(tour.getHotel().getId());
        if (isHotelExist) {
            tour.setImageSrc(imageService.saveImage(file));
            tourRepository.save(tour);
        } else throw new NotFoundException("Tour can not be created because hotel with id: " + tour.getHotel().getId() + " not exist.");
    }

    public void removeTour(Long tourId) throws ReservationsExistException, DeletionException, FileNotFoundException {
        Optional<Tour> opTour = tourRepository.findById(tourId);
        if (opTour.isPresent()) {
            Long reservationsCount = reservationRepository.countByTour(opTour.get());
            if (reservationsCount == 0) {
                imageService.deleteFile(opTour.get().getImageSrc());
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
