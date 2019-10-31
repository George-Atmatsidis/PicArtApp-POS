package com.chumbok.pos.service;

import com.chumbok.pos.entity.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);

    void updateUser(User user);
    User getUser(long id);

    List<User> getAllUsers();
}