package com.chumbok.pos.service;

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
    public void saveUser(User user) {
        //if (userRepository.isExist(user.getEmail())) {
        //  throw new IllegalArgumentException("El email proporcionado ya está registrado.");
        //} else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(1);
            Role userRole = roleRepository.findByRole("ADMIN");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userRepository.save(user);
        // }
    }


    @Override
    public void saveNonAdminUser(User user) {
        //if (userRepository.isExist(user.getEmail())) {
        //throw new IllegalArgumentException("El email proporcionado ya está registrado.");
        //} else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(1);
            Role userRole = roleRepository.findByRole("USER");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            userRepository.save(user);
        // }
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
        //I hope this shit works, I'm very sorry, mom
        Role userRole = roleRepository.findByRole("" + user.getRoles());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

    }

    @Override
    public void makeUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1); //always set as active when is a new user
        userRepository.save(user);
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
     * Yeah, right; yeah right (?
     *
     * @param user
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