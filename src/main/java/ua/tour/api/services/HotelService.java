package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Hotel;
import ua.tour.api.entities.Tour;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.repo.HotelRepository;
import ua.tour.api.repo.TourRepository;

import java.util.Optional;

@Service
public class HotelService {

    private HotelRepository hotelRepository;
    private TourRepository tourRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, TourRepository tourRepository) {
        this.hotelRepository = hotelRepository;
        this.tourRepository = tourRepository;
    }

    public Iterable<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) throws NotFoundException {
        Optional opHotel = hotelRepository.findById(id);
        if (opHotel.isPresent()) {
            return (Hotel) opHotel.get();
        } else throw new NotFoundException("Hotel with id: " + id + " not found");
    }

    public long addHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return hotel.getId();
    }

    public void deleteHotel(Long hotelId) throws NotFoundException, DeletionException {
        Optional<Hotel> opHotel = hotelRepository.findById(hotelId);
        if (opHotel.isPresent()) {
            Optional<Tour> opTour = tourRepository.findFirstByHotel(opHotel.get());
            if (opTour.isPresent()) {
                throw new DeletionException("Unable to delete hotel because there are tours to this hotel.");
            } else hotelRepository.deleteById(hotelId);

        } else throw new NotFoundException("Unable to delete non-existent hotel.");

    }

    public void updateHotel(Hotel hotel) throws NotFoundException {
        if (hotelRepository.existsById(hotel.getId())) {
            hotelRepository.save(hotel);
        } else throw new NotFoundException("Unable to update non-existent hotel with id: " + hotel.getId());

    }
}
