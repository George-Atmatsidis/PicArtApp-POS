package com.chumbok.pos.service;

import com.chumbok.pos.dto.UserDTO;
import com.chumbok.pos.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    //update a user but not the password
    void updateUser(UserDTO userDTO, long id);

    void makeUser(UserDTO userDTO) throws Exception;

    //Aquí se pide el usuario, es un get; pretty straightforward if you ask me, bro.
    User getUser(long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    List<User> getAllUsers();

    //here's when it goes to shit
    void disableUser(User user);

    User findOne(long id);
}