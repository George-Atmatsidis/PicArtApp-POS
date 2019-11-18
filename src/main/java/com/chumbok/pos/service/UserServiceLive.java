package com.chumbok.pos.service;

import com.chumbok.pos.dto.UserDTO;
import com.chumbok.pos.entity.Role;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.repository.RoleRepository;
import com.chumbok.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceLive implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public void updateUser(User user) {
        User userById = userRepository.findOne(user.getId());
        userById.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userById.setEmail(user.getEmail());
        userById.setActive(user.getActive());
        userById.setFirstName(user.getFirstName());
        userById.setLastName(user.getLastName());
        userById.setRoles(user.getRoles());
        Role userRole = roleRepository.findByRole("" + user.getRoles());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
    }

    /**
     * Takes one user and updates its details. If we receive something
     * else than "ADMIN" as a role, it defaults to "USER".
     *
     * @param userDTO which is the user to update
     * @param id      of the user we are trying to update
     */
    @Override
    public void updateUser(UserDTO userDTO, long id) {
        User userById = userRepository.findOne(id);
        userById.setLastName(userDTO.getLastName()); //Sets first name
        userById.setFirstName(userDTO.getFirstName()); //Sets last name
        userById.setEmail(userDTO.getEmail()); //sets email as intended
        if (userDTO.getRole().equals("ADMIN")) {
            Role userRole = roleRepository.findByRole("ADMIN"); //sets role as admin
        } else {
            //I think, we should set anything else as a simple user. Even if they
            //manage to send something that doesn't make sense.
            Role userRole = roleRepository.findByRole("USER"); //sets role as user
        }
        if (userById.getPassword().equals(userDTO.getPassword())) { //la contraseña no ha cambiado
            //así que no se altera
        } else { //la contraseña ha cambiado
            userById.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
    }

    /**
     * This method makes use of userDTO to ensure a validated user is being
     * made, as such, it asumes both passwords match and so on. Sets user's
     * roles as defined. Ciphers user's password and sets it as such.
     * @param userDTO which is the data transfer object for the new user
     */
    @Override
    public void makeUser(UserDTO userDTO) {
        User user = new User();
        user.setLastName(userDTO.getLastName()); //Sets last name
        user.setFirstName(userDTO.getFirstName()); //Sets first name
        //Checks for user or admin role; if it isn't one of them, defaults to user (?
        Role userRole; //here's where we save the actual
        if (userDTO.getRole() != null) {
            if (userDTO.getRole().equals("ADMIN")) { //if they send an admin, we do
                userRole = roleRepository.findByRole("ADMIN"); //sets role as admin
            } else {
                //I think, we should set anything else as a simple user. Even if they
                //manage to send something that doesn't make sense.
                userRole = roleRepository.findByRole("USER"); //sets role as user
            }
        } else { //default to user if anything else
            userRole = roleRepository.findByRole("USER"); //sets role as user
        }
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole))); //finally, we set such role
        user.setEmail(userDTO.getEmail()); //sets email as intended
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword())); //ciphers user password and sets it as such
        user.setActive(1); //always set as active when is a new user
        userRepository.save(user); //saves the new user
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * So, here's the thing, this should disable said user.
     * Nothing flashy, just pure disable of a user.
     * @param user which is the user to be disabled
     */
    @Override
    public void disableUser(User user) {
        User userById = userRepository.findOne(user.getId());
        userById.setActive(0);
    }

    /**
     * Here's the thing again: I have not enough clue as for what is happening.
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<User> findAllByPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }
}