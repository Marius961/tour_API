package ua.tours.api.dao.interfaces;

import ua.tours.api.models.User;

public interface UserDao {

    User getUserByID(int id);
    void insertUser(User user);
    void updateUser(User user);
    void removeUser(int userId);
}
