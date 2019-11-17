package com.chumbok.pos.controller;

import com.chumbok.pos.dto.PersistedObjId;
import com.chumbok.pos.dto.UserDTO;
import com.chumbok.pos.entity.Role;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.repository.RoleRepository;
import com.chumbok.pos.service.UserService;
import com.chumbok.pos.service.UserServiceLive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users/makeModify", method = RequestMethod.GET)
    public ModelAndView showUsers(@RequestParam(required = false) Long userId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (userId != null) {
            User user = userService.getUser(userId);
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPassword(user.getPassword());
            userDTO.setConfirmPassword(user.getPassword());
            modelAndView.addObject("userDTO", userDTO);
        } else {
            modelAndView.addObject("userDTO", new UserDTO());
        }

        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(path = "/users/createModify", method = RequestMethod.POST)
    public ModelAndView createUpdateUser(@RequestParam(value = "id", required = false) Long id, @Valid UserDTO userDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (id == null) {
            if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) { //first of all, both passwords must match
                userService.makeUser(userDTO); //saves the user as a real user
            }
            modelAndView.addObject("successMessage", "El usuario se ha registrado exitosamente.");
            modelAndView.addObject("userDTO", new UserDTO());
            modelAndView.setViewName("registration");
        } else if (id != null) {
            userService.updateUser(userDTO, id); //sends userDTO for validation and id
            modelAndView.addObject("successMessage", "Usuario modificado correctamente.");
            modelAndView.addObject("userDTO", userDTO);
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
   /* @Autowired
    private UserService userService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<User> getUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public PersistedObjId createUser(@RequestBody @Valid User user) {
        userService.createUser(user);
        return new PersistedObjId(user.getId());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        return user;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user) {
        user.setId(id);
        userService.updateUser(user);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }*/
}




