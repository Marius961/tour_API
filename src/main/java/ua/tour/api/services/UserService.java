package ua.tour.api.services;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ua.tour.api.entities.Role;
import ua.tour.api.entities.User;
import ua.tour.api.exceptions.UserRegistrationFailedException;
import ua.tour.api.repo.UserRepository;

import javax.validation.constraints.NotBlank;
import java.nio.file.AccessDeniedException;
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
        if (!userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            try {
                user.setId(null);
                userRepository.save(user);
            } catch (HibernateException e) {
                throw new UserRegistrationFailedException("Failed to register new user.");
            }
        } else throw new UserRegistrationFailedException("Failed to register new user, because username or email already exist.");

    }

    public Page<User> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, 15));
    }

    public void addAdminRole(Long userId) {
        Optional opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User user = (User) opUser.get();
            if (!user.getRoles().contains(Role.ADMIN)) {
                user.addRole(Role.ADMIN);
            }
            userRepository.save(user);
        }
    }

    public void removeAdminRole(Long userId) {
        Optional opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User user = (User) opUser.get();
            if (user.getRoles().contains(Role.ADMIN)) {
                user.removeRole(Role.ADMIN);
            }
            userRepository.save(user);
        }
    }

    public void updateUserData(
            String username,
            String email,
            String fullName,
            String mobileNumber
    ) {
        User user = (User) loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            if (username != null && !userRepository.existsByUsername(username))
                user.setUsername(username);
            if (email != null && !userRepository.existsByEmail(email))
                user.setEmail(email);
            if (fullName != null)
                user.setFullName(fullName);
            if (mobileNumber != null)
                user.setMobileNumber(mobileNumber);
            userRepository.save(user);
        }
    }

    public boolean isRegistered(String username) throws HibernateException {
        return userRepository.findByUsername(username) != null;
    }

    public void changePassword(@NotBlank String oldPassword, @NotBlank String newPassword) throws AccessDeniedException {
        User user = (User) loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            if (BCrypt.checkpw(oldPassword, user.getPassword())) {
                user.setPassword(newPassword);
                userRepository.save(user);
            } else throw new AccessDeniedException("Invalid current password");
        } else throw new UsernameNotFoundException("Cannot find current user");
}
    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
