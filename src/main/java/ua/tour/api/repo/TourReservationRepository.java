package ua.tour.api.repo;

import org.springframework.data.repository.CrudRepository;
import ua.tour.api.entities.TourReservation;

public interface TourReservationRepository extends CrudRepository<TourReservation, Integer> {
}
