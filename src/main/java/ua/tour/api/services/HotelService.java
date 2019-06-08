package ua.tour.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.tour.api.entities.Hotel;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.repo.HotelRepository;
import ua.tour.api.repo.TourRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class HotelService {

    private HotelRepository hotelRepository;
    private TourRepository tourRepository;
    private ImageService imageService;

    public HotelService(HotelRepository hotelRepository, TourRepository tourRepository, ImageService imageService) {
        this.hotelRepository = hotelRepository;
        this.tourRepository = tourRepository;
        this.imageService = imageService;
    }

    public Page<Hotel> getAllHotels(int page) {
        return hotelRepository.findAll(PageRequest.of(page, 15));
    }

    public Hotel getHotelById(Long id) throws NotFoundException {
        Optional opHotel = hotelRepository.findById(id);
        if (opHotel.isPresent()) {
            return (Hotel) opHotel.get();
        } else throw new NotFoundException("Hotel with id: " + id + " not found");
    }

    public long addHotel(Hotel hotel, MultipartFile file) throws IOException {
        if (!hotelRepository.existsByName(hotel.getName())) {
            hotel.setId(null);
            hotel.setImageSrc(imageService.saveImage(file));
            hotelRepository.save(hotel);
        } else throw new IllegalArgumentException("Hotel with name: " + hotel.getName() + " already exists");

        return hotel.getId();
    }

    public void deleteHotel(Long hotelId) throws NotFoundException, DeletionException, FileNotFoundException {
        Optional<Hotel> opHotel = hotelRepository.findById(hotelId);
        if (opHotel.isPresent()) {
            Optional<Tour> opTour = tourRepository.findFirstByHotel(opHotel.get());
            if (opTour.isPresent()) {
                throw new DeletionException("Unable to delete hotel because there are tours to this hotel.");
            } else {
                imageService.deleteImage(opHotel.get().getImageSrc());
                hotelRepository.deleteById(hotelId);
            }

        } else throw new NotFoundException("Unable to delete non-existent hotel.");
    }


    public void updateHotel(Hotel hotel) throws NotFoundException {
        if (hotelRepository.existsById(hotel.getId())) {
            if (hotelRepository.countByNameAndNotId(hotel.getName(), hotel.getId()) == 0) {
                hotelRepository.save(hotel);
            } else throw new IllegalArgumentException("Cannot update hotel. Hotel with name: " + hotel.getName() + "already exists");
        } else throw new NotFoundException("Unable to update non-existent hotel with id: " + hotel.getId());

    }
}
