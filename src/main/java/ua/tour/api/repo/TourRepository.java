package ua.tour.api.repo;

import org.springframework.data.repository.CrudRepository;
import ua.tour.api.entities.Hotel;
import ua.tour.api.entities.Tour;

import java.util.Optional;

public interface TourRepository extends CrudRepository<Tour, Long> {

    Optional<Tour> findFirstByHotel(Hotel hotel);
}
