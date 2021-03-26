package com.libraryRESTful.dao;

import com.libraryRESTful.model.User;

import java.util.List;

public interface UserDAO {
    User createUser(User user);
    String deleteUser(long id);
    User updateUser(long id,User user);
    User getUserById(long id);
    List<User> getAllUsers();
    String takeBook(long bookID,long userID);
    String returnBook(long bookID,long userID);

}