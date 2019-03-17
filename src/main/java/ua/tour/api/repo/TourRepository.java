package ua.tour.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tour.api.entities.Hotel;
import ua.tour.api.entities.Tour;

import java.util.Optional;

public interface TourRepository extends PagingAndSortingRepository<Tour, Long> {

    Optional<Tour> findFirstByHotel(Hotel hotel);

    boolean existsByTitleOrDescription(String title, String description);
}
