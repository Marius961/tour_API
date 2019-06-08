package ua.tour.api.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.tour.api.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
