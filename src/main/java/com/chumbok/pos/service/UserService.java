package com.chumbok.pos.service;

import com.chumbok.pos.dto.UserDTO;
import com.chumbok.pos.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    //What have we done to get this shit
    void updateUser(User user); //TODO deprecate this shit

    //update a user but not the password
    void updateUser(UserDTO userDTO, long id);

    void makeUser(UserDTO userDTO) throws Exception;

    //Aquí se pide el usuario, es un get; pretty straightforward if you ask me, bro.
    User getUser(long id);

    @Secured({"ROLE_ADMIN"})
    List<User> getAllUsers();

    //here's when it goes to shit
    void disableUser(User user);
}