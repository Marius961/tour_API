package ua.tour.api.repo;

import org.springframework.data.repository.CrudRepository;
import ua.tour.api.entities.Hotel;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
}
