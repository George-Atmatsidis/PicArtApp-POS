package com.chumbok.pos.service;

import com.chumbok.pos.dto.CustomerDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomerServiceLive implements CustomerService {
    @Autowired //which is where we are redirecting some of this shit
            CustomerRepository customerRepository;

    /**
     * This method is a justification for the findById found on
     * the customer repository. As simple as that, gets an id,
     * returns the customer on that id.
     *
     * @param id of the customer to look for
     * @return said customer
     */
    @Override
    public Customer findById(long id) {
        return customerRepository.findOne(id);
    }

    /**
     * This one gets a first name, a last name and... then gets the user.
     *
     * @param firstName of the customer to look for
     * @param lastName  of the customer to look for, yeah
     * @return said customer, i guess
     */
    @Override
    public Customer findByName(String firstName, String lastName) {
        return customerRepository.findCustomerByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Same as the rest, checks wheter a customer exist based on first and last name
     *
     * @param curp of the customer to look for
     * @return a flag if the customer exist... or not
     */
    @Override
    public boolean isExist(String curp) {
        return customerRepository.isExist(curp);
    }

    /**
     * This method looks for a customer and gets it's name.
     *
     * @param id of the customer to get their name
     * @return name of said customer
     */
    @Override
    public String getFullName(long id) {
        return customerRepository.findOne(id).getFirstName() + " " + customerRepository.findOne(id).getLastName();
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setCurp(customerDTO.getCurp());
        customer.setEmail(customerDTO.getEmail());
        customer.setNameAval(customerDTO.getNameAval());
        customer.setAvalPhone(customerDTO.getAvalPhone());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
