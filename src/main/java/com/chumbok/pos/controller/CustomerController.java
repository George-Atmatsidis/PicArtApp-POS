package com.chumbok.pos.controller;

import com.chumbok.pos.dto.CustomerDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * This method maps the page for customer registration
     *
     * @return a page with a customer to be filled
     */
    @RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
    public ModelAndView registrationOfAClientView() {
        ModelAndView modelAndView = new ModelAndView();
        CustomerDTO customerDTO = new CustomerDTO();
        modelAndView.addObject("customerDTO", customerDTO);
        modelAndView.setViewName("registrationClient");
        return modelAndView;
    }

    @RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        customerService.createCustomer(customerDTO); //customer service is gonna create this customer for me
        modelAndView.addObject("customerDTO", new CustomerDTO());
        modelAndView.setViewName("registrationClient");
        return modelAndView;
    }

}
