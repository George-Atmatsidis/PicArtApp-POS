package com.chumbok.pos.controller;

import com.chumbok.pos.dto.UserDTO;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')") //just the admin should be able to modify other users
    @RequestMapping(path = "/user/makeModify", method = RequestMethod.GET)
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

    @PreAuthorize("hasAuthority('ADMIN')") //just the admin should be able to modify other users
    @RequestMapping(path = "/user/createModify", method = RequestMethod.POST)
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

    @RequestMapping(path = "/manejoDeUsuarios", method = RequestMethod.GET)
    public ModelAndView showMainReturnPage() {
        return new ModelAndView("registrationMain");
    }

    /**
     * Gets a list with all the users, adds it to a view and sends it.
     *
     * @return view with the users in it.
     */
    @PreAuthorize("hasAuthority('ADMIN')") //just the admin can see other users
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView showUserList() {
        ModelAndView modelAndView = new ModelAndView("userList");
        List<User> list = userService.getAllUsers();
        modelAndView.addObject("userList", list);
        return modelAndView;
    }


    @PreAuthorize("hasAuthority('ADMIN')") //just an admin can disable another user
    @RequestMapping(value = "/disable/{user}")
    public ModelAndView disableUser(@PathVariable("user") Long user) {
        if (user != null) {
            userService.disableUser(userService.findOne(user));
        } else {
            System.err.println("wtf, why");
        }
        return showUserList();
    }
}




