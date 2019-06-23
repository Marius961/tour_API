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

    // метод для додавання готелю, приймає екземпляр класу Hotel і файл (фотографію)
    public long addHotel(Hotel hotel, MultipartFile file) throws IOException {
        // перевірка чи готель з таким іменем вже існує
        if (!hotelRepository.existsByName(hotel.getName())) {
            // якщо готел не існує, отриманому вище екземпляну вставляється id із значенням null, для того, щоб уникнути перезапису
            hotel.setId(null);
            // за допомогою imageService зберігається отримане зображення (у папку на диску), після цього назва зображення записується у екземпляр класу готелю
            hotel.setImageSrc(imageService.saveImage(file));
            // після вставки всіх даних, готель зберігається у базу даних
            hotelRepository.save(hotel);
        // у випадку, якщо готель з таким іменем вже існує, повертається виключення з відповідним повідомленням
        } else throw new IllegalArgumentException("Hotel with name: " + hotel.getName() + " already exists");
        // якщо не виникло ніяких помилок, метод повертає id доданого готелю
        return hotel.getId();
    }

    // метод для видалення готелю, приймає id готелю, який потрібно видалити
    public void deleteHotel(Long hotelId) throws NotFoundException, DeletionException, FileNotFoundException {
        // спроба отримати готель з бази даних по id
        Optional<Hotel> opHotel = hotelRepository.findById(hotelId);
        // перевірка чи готель, який потрібно видалити, існує
        if (opHotel.isPresent()) {
            // якщо готель існує, проводиться перевірка чи цей готель присутній у якомусь турі (якщо так, тоді його не можна видаляти)
            Optional<Tour> opTour = tourRepository.findFirstByHotel(opHotel.get());
            // перевірка чи тур існує
            if (opTour.isPresent()) {
                // якщо тур існує, і даний готель використовується, повертається виключення з відповідним повідомленням
                throw new DeletionException("Unable to delete hotel because there are tours to this hotel.");
            } else {
                // якщо готель не використовується, спочатку видаляється зображення
                imageService.deleteImage(opHotel.get().getImageSrc());
                // після видалення зображення, готель видаляється з бази даних
                hotelRepository.deleteById(hotelId);
            }
        // якщо готель, який потрібно видалити, відсутній - повертається виключення з повідомленням
        } else throw new NotFoundException("Unable to delete non-existent hotel.");
    }

    // метод для редагування даних готелю, приймає екземпляр класу готелю, з даними, які потрібно оновити
    public void updateHotel(Hotel hotel) throws NotFoundException {
        // перевірка чи готель, який буде редагуватись, існує
        if (hotelRepository.existsById(hotel.getId())) {
            // перевірка на те, чи ім'я відредагованого готелю не співпадає з вже існуючими, окрім поточного
            if (hotelRepository.countByNameAndNotId(hotel.getName(), hotel.getId()) == 0) {
                // якщо ім'я не співпадає ні з одним з інших готелів - зберігає відредагований готель
                hotelRepository.save(hotel);
            // виключення повертається, якщо назва відредагованого готелю співпала з вже існуючим
            } else throw new IllegalArgumentException("Cannot update hotel. Hotel with name: " + hotel.getName() + "already exists");
        // виключення повертається, якщо готель, який потрібно відредагувати, не існує
        } else throw new NotFoundException("Unable to update non-existent hotel with id: " + hotel.getId());

    }
}
