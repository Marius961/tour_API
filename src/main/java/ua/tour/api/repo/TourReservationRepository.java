package ua.tour.api.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tour.api.entities.Tour;
import ua.tour.api.entities.TourReservation;
import ua.tour.api.entities.User;

public interface TourReservationRepository extends PagingAndSortingRepository<TourReservation, Long> {

    Page<TourReservation> findAllByUser(User user, Pageable pageable);

    Long countByTour(Tour tour);
}
