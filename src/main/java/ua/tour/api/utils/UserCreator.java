package ua.tour.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.tour.api.entities.Role;
import ua.tour.api.entities.User;
import ua.tour.api.repo.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class UserCreator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        addAdmin();


    }


    private void addAdmin() {
        User admin = new User("admin", "admin@gmail.com", passwordEncoder.encode("admin"), "Admin Admin", "1481987516484");
        admin.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN)));
        admin.setActive(true);
        userRepository.save(admin);
    }
}
