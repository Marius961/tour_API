package ua.tour.api.repo;

import org.springframework.data.repository.CrudRepository;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.entities.User;

public interface TourReservationRepository extends CrudRepository<TourReservation, Long> {

    Iterable<TourReservation> findAllByUser(User user);
}
