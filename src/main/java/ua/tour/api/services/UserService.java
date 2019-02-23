package ua.tour.api.services;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Role;
import ua.tour.api.entities.User;
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

    public boolean createUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        try {
            userRepository.save(user);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void setAsAdmin(int userId) {
        Optional opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User user = (User) opUser.get();
            user.addRole(Role.ADMIN);
            System.out.println(user.getRoles());
            userRepository.save(user);
        }
    }
}
