package ua.tour.api.repo;

import org.springframework.data.repository.CrudRepository;
import ua.tour.api.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
