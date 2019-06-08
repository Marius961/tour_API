package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.exceptions.ReservationsExistException;
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
        boolean isTourTitleOrDescriptionExist = tourRepository.existsByTitleOrDescription(tour.getTitle(), tour.getDescription());
        if (isHotelExist && !isTourTitleOrDescriptionExist) {
            tour.setId(null);
            tour.setImageSrc(imageService.saveImage(file));
            tourRepository.save(tour);
        } else throw new NotFoundException("Tour can not be created because hotel with id: " + tour.getHotel().getId() + " not exist.");
    }

    public void removeTour(Long tourId) throws ReservationsExistException, DeletionException, FileNotFoundException {
        Optional<Tour> opTour = tourRepository.findById(tourId);
        if (opTour.isPresent()) {
            Long reservationsCount = reservationRepository.countByTour(opTour.get());
            if (reservationsCount == 0) {
                imageService.deleteImage(opTour.get().getImageSrc());
                tourRepository.deleteById(tourId);
            } else throw new ReservationsExistException("The tour can not be deleted because reservations have been found.");
        } else throw new DeletionException("Unable to delete tour, because it not exist");
    }


    public Page<Tour> getAllTours(int page) {
        return tourRepository.findAll(PageRequest.of(page, 10));
    }

    public void updateTour(Tour tour) throws NotFoundException {
        if (tourRepository.existsById(tour.getId())) {
            boolean isHotelExist = hotelRepository.existsById(tour.getHotel().getId());
            int existedTours = tourRepository.countByTitleOrDescription(tour.getId(), tour.getTitle(), tour.getDescription());
            if (isHotelExist) {
                if (existedTours == 0) {
                    tourRepository.save(tour);
                } else throw new IllegalArgumentException("Tour with this title or description already exist");
            } else throw new NotFoundException("Cannot update hotel from tour, hotel not exist");
        } else throw new NotFoundException("Can not update tour, because it does not exist");
    }
}
