package ua.tour.api.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.tour.api.entities.Tour;

import java.util.Date;

public interface TourRepository extends CrudRepository<Tour, Long> {
}
