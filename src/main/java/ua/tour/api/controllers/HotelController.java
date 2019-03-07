package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.tour.api.entities.Hotel;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.services.HotelService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public Iterable<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) throws NotFoundException {
        return hotelService.getHotelById(id);
    }

    @PutMapping
    public void updateHotel(@Valid @RequestBody Hotel hotel) throws NotFoundException {
        hotelService.updateHotel(hotel);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Long id) throws NotFoundException, DeletionException {
        hotelService.deleteHotel(id);
    }

    @PostMapping
    public Map<String, Long> addHotel(@Valid @RequestBody Hotel hotel) {
        return Collections.singletonMap("hotelId", hotelService.addHotel(hotel));
    }
}
