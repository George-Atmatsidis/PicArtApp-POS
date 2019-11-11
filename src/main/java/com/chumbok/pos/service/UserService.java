package com.chumbok.pos.service;

import com.chumbok.pos.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    void saveUser(User user);

    //I'm sorry, god
    void saveNonAdminUser(User user);

    //What have we done to get this shit
    void updateUser(User user);

    void makeUser(User user);

    //Aquí se pide el usuario, es un get; pretty straitghforward if you ask me, bro.
    User getUser(long id);

    List<User> getAllUsers();

    //here's when it goes to shit
    void disableUser(User user);

    //this might actually work
    Page<User> findAllByPage(Pageable pageable);
}