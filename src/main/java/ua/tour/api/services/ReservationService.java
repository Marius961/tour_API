package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Tour;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.entities.User;
import ua.tour.api.exceptions.NotFoundException;
import ua.tour.api.exceptions.SeatsCountOverflowException;
import ua.tour.api.repo.TourRepository;
import ua.tour.api.repo.TourReservationRepository;

import java.security.Principal;
import java.util.Optional;

@Service
public class ReservationService {

    private TourReservationRepository tourReservationRepository;
    private TourRepository tourRepository;
    private UserService userService;

    @Autowired
    public ReservationService(
            TourReservationRepository tourReservationRepository,
            UserService userService,
            TourRepository tourRepository) {

        this.tourReservationRepository = tourReservationRepository;
        this.userService = userService;
        this.tourRepository = tourRepository;
    }

    // метод для бронювання місця, приймає сформований екземпляр класу TourReservation
    public void addReservation(TourReservation reservation, Principal principal) throws SeatsCountOverflowException, NotFoundException {
        // спроба знайти у базі даних тур, місце в якому, потрібно зарезервувати
        Optional<Tour> opTour = tourRepository.findById(reservation.getTour().getId());
        // перевірка чи тур, місце в якому потрібно забронювати, існує
        if (opTour.isPresent()) {
            // якщо тур існує, посилання на нього передається у новий екземпляр класу
            Tour tour = opTour.get();
            // Підрахунок всіх бронювань на даний тур у базі даних
            Long reservationCount = tourReservationRepository.countByTour(tour);
            // перевірка чи місце у турі можна зарезервувати (чи кількість можливих резервувань не максимальна)
            if (reservationCount < tour.getSeatCount()) {
                // отримання поточного користувача з бази даних, за допомогою об'єкту principal та імені користувача
                User currentUser = (User) userService.loadUserByUsername(principal.getName());
                // перевірка чи користувач існує
                if (currentUser != null) {
                    // встановлення id резервування у значення null щоб уникнути перезапису вже існуючих даних
                    reservation.setId(null);
                    // встанновлення активності резервування у значення true
                    reservation.setActive(true);
                    // присвоєння посилання на користувача у резервуванні
                    reservation.setUser(currentUser);
                    // збереження резервування у базу даних
                    tourReservationRepository.save(reservation);
                }
            // виключення повертається якщо перевищена кількість доступних місць у турі
            } else throw new SeatsCountOverflowException("Seats for the selected tour have expired");
        // виключення повертається, якщо проходить спроба зарезервувати місце у неіснуючому турі
        } else throw new NotFoundException("Tour with id: " + reservation.getTour().getId() + " not found");

    }

    // метод для виводу списку резервувань (по сторінці), в метод передається номер сторінки та об'єкт Principal
    public Page<TourReservation> getAllUserReservations(int page, Principal principal) throws UsernameNotFoundException {
        // спроба завантажити поточного користувача з бази даних
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        // перевірка чи користувач існує
        if (currentUser != null) {
            // якщо користувач існує, метод повертає список резервувань користувача, з вказаною сторінкою
            return tourReservationRepository.findAllByUser(currentUser, PageRequest.of(page, 15));
        // якщо користувача не буде знайдено - буде повернено відповідне повідомлення
        } else throw new UsernameNotFoundException("User with username " + principal.getName() + " not found");
    }

    // скасування бронювання, метод приймає id бронювання, яке потрібно скасувати
    public void cancelReservation(Long reservationId) throws NotFoundException {
        // спроба отримати резервування з бд
        Optional<TourReservation> opReservation = tourReservationRepository.findById(reservationId);
        // перевірка чи резервування існує
        if (opReservation.isPresent()) {
            // присвоєння посилання на об'єкт новому екземпляру класу
            TourReservation reservation = opReservation.get();
            // встановлення статусу активності резервування у значення false
            reservation.setActive(false);
            // оновлення даних у бд
            tourReservationRepository.save(reservation);
        // виключення повертається, в разі відсутності резервування у бд
        } else throw new NotFoundException("Reservation with id: " + reservationId + " not found");
    }

    // вивід всіх резервувань (для адміністратора), приймає номер сторінки в якості параметра
    public Page<TourReservation> getAllReservations(int page) {
        return tourReservationRepository.findAll(PageRequest.of(page, 16));
    }
}
