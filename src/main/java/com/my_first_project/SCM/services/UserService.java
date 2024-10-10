package com.my_first_project.SCM.services;

import java.util.List;
import java.util.Optional;

import com.my_first_project.SCM.entities.User;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExistById(String userId);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();
}