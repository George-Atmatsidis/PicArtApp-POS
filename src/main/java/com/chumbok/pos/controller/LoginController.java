package com.chumbok.pos.controller;


import com.chumbok.pos.dto.UserDTO;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login", ""}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()); //
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')") //just admins can see the new user form
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = new UserDTO();
        modelAndView.addObject("userDTO", userDTO);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')") //just admins can register new users
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserDTO userDTO, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.findUserByEmail(userDTO.getEmail()) != null) { //si ya hay un usuario registrado con ese email
            bindingResult
                    .rejectValue("email", "error.user",
                            "Ya existe un usuario registrado con ese correo electrónico.");
        }
//        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
//            bindingResult
//                    .rejectValue("confirmPassword", "error.confirmPassword"
//                            , "Las contraseñas no coinciden, por favor verifique");
//        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            //first of all, both passwords must match
            if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                modelAndView.addObject("debugMessage", "Password doesn't match");
            }
            userService.makeUser(userDTO); //saves the user as a real user
            modelAndView.addObject("successMessage", "El usuario se ha registrado exitosamente.");
            modelAndView.addObject("userDTO", new UserDTO());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }


}