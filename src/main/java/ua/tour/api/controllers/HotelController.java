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

    // метод GET, /api/hotel. Метод дозволяє отримати список всіх готелів, приймає номер сторінки як URL параметр
    @GetMapping
    public Page<Hotel> getAllHotels(@RequestParam(name = "p") int page) {
        return hotelService.getAllHotels(page);
    }

    // метод GET, /api/hotel/{id}. Метод повертає готель по id, яке передається як URL параметр
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) throws NotFoundException {
        return hotelService.getHotelById(id);
    }

    // метод PUT, /api/hotel. Метод дозволяє оновити дані готелю, приймає готель у тілі запиту, у форматі JSON
    @PutMapping
    public void updateHotel(@Valid @RequestBody Hotel hotel) throws NotFoundException {
        hotelService.updateHotel(hotel);
    }

    // метод DELETE, /api/hotel/{id}. Метод дозволяє видалити готель по ID яке передається як path параметр у URL
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Long id) throws NotFoundException, DeletionException, FileNotFoundException {
        hotelService.deleteHotel(id);
    }

    // метод POST, /api/hotel. Використовується для додавання нового готелю, приймає об'єкт готелю у тілі запиту у форматі JSON, а також файл (фото)
    @PostMapping
    public Map<String, Long> addHotel(
            @Valid @RequestPart(name = "hotel") Hotel hotel,
            @RequestPart(name = "image")MultipartFile file) throws IOException {

        return Collections.singletonMap("hotelId", hotelService.addHotel(hotel, file));
    }
}
