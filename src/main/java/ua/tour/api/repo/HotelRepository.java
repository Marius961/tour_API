package ua.tour.api.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tour.api.entities.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {

    boolean existsByName(String name);

    @Query("SELECT count(h) FROM Hotel h WHERE h.name = :name AND h.id <> :id")
    Integer countByNameAndNotId(String name, Long id);
}
