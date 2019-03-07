package ua.tour.api.services;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Role;
import ua.tour.api.entities.User;
import ua.tour.api.exceptions.UserRegistrationFailedException;
import ua.tour.api.repo.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User applicationUser = userRepository.findByUsername(s);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(s);
        }
        return applicationUser;
    }

    public void createUser(User user) throws UserRegistrationFailedException {
        if (userRepository.findFirstByUsernameOrEmail(user.getUsername(), user.getEmail()) == null) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            try {
                userRepository.save(user);
            } catch (HibernateException e) {
                throw new UserRegistrationFailedException("Failed to register new user.");
            }
        } else throw new UserRegistrationFailedException("Failed to register new user, because username or email already exist.");

    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void setAsAdmin(Long userId) {
        Optional opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User user = (User) opUser.get();
            user.addRole(Role.ADMIN);
            System.out.println(user.getRoles());
            userRepository.save(user);
        }
    }

    public boolean isRegistered(String username) throws HibernateException {
        return userRepository.findByUsername(username) != null;
    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
