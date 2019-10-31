package com.chumbok.pos.service;

import com.chumbok.pos.entity.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    User getUser(long id);
}