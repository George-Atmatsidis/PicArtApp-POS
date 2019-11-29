package com.chumbok.pos.controller;

import com.chumbok.pos.dto.CustomerDTO;
import com.chumbok.pos.dto.PagesDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public ModelAndView createNewCustomer(@Valid CustomerDTO customerDTO) {
        ModelAndView modelAndView = new ModelAndView();
        customerService.createCustomer(customerDTO); //customer service is gonna create this customer for me
        modelAndView.addObject("successMessage", "Cliente registrado exitosamente.");
        modelAndView.addObject("customerDTO", new CustomerDTO());
        modelAndView.setViewName("registrationClient");
        return modelAndView;
    }

    //List<Customer> customerList = customerService.findAll();
    @RequestMapping(value = "/customer/list/{page}", method = RequestMethod.GET)
    public ModelAndView viewCustomerList(@PathVariable("page") int page, @RequestParam(required = false) Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        List<Customer> customerList = customerService.findAll();
        List<Customer> justTheCustomerInSaidPage;
        //here goes nothing
        int listSize = 3; //a variable, just in case :)
        if (pageSize != null) {
            listSize = pageSize;
        }
        if (page == 1) {
            int i = 0;
            justTheCustomerInSaidPage = new ArrayList<>();
            while (i < customerList.size() && i < listSize) {
                justTheCustomerInSaidPage.add(customerList.get(i));
                i++;
            }
        } else {
            justTheCustomerInSaidPage = new ArrayList<>();
            int i = (page - 1) * listSize;
            int fin = i + listSize;
            while (i < customerList.size() && i < fin) {
                justTheCustomerInSaidPage.add(customerList.get(i));
                i++;
            }
        }
        int totalPages = customerList.size() / listSize;
        if (customerList.size() % listSize > 0) {
            totalPages += 1;
        }
        List<PagesDTO> listaDePaginas = new ArrayList<>();
        if (page == 1) {
            listaDePaginas.add(new PagesDTO("Anterior", page, "disabled"));
        }
        if (page > 1) {
            listaDePaginas.add(new PagesDTO("Anterior", page - 1));
        }
        if (page < totalPages) {
            listaDePaginas.add(new PagesDTO("Siguiente", page + 1));
        }
        if (page == totalPages) {
            listaDePaginas.add(new PagesDTO("Siguiente", page, "disabled"));
        }
        modelAndView.addObject("listaDePaginas", listaDePaginas);
        //here ends nothing
        modelAndView.addObject("customerList", justTheCustomerInSaidPage);
        modelAndView.setViewName("customerList");
        return modelAndView;
    }
}
