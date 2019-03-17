package ua.tour.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.tour.api.entities.Hotel;
import ua.tour.api.exceptions.DeletionException;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.services.HotelService;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @GetMapping("/{page}")
    public Page<Hotel> getAllHotels(@PathVariable int page) {
        return hotelService.getAllHotels(page);
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
    public void deleteHotel(@PathVariable Long id) throws NotFoundException, DeletionException, FileNotFoundException {
        hotelService.deleteHotel(id);
    }

    @PostMapping
    public Map<String, Long> addHotel(
            @Valid @RequestPart(name = "hotel") Hotel hotel,
            @RequestPart(name = "image")MultipartFile file) throws IOException {

        return Collections.singletonMap("hotelId", hotelService.addHotel(hotel, file));
    }
}
