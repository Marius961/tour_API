package ua.tour.api.repo;

import org.springframework.data.repository.CrudRepository;
import ua.tour.api.entities.Tour;

public interface TourRepository extends CrudRepository<Tour, Integer> {
}
