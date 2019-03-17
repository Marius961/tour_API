package ua.tour.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tour.api.entities.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
}
