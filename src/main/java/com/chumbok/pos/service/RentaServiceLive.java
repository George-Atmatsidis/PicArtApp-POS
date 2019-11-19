package com.chumbok.pos.service;

import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.entity.Renta;
import com.chumbok.pos.repository.*;
import com.chumbok.pos.utility.DateConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Transactional
@Service
public class RentaServiceLive implements RentaService {

    @Autowired //idk, plZ hlP
            ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentaRepository rentaRepository;

    @Autowired
    CustomerRepository customerRepository;

    /**
     * This method creates an renta object
     *
     * @param rentaDTO transactional object with the necessary data
     * @throws Exception in case a wrong date has been gotten
     */
    @Override
    public void createRenta(RentaDTO rentaDTO) throws Exception {
        Renta renta = new Renta();
        renta.setPrice(rentaDTO.getPrice() * rentaDTO.getQuantity()); //establece el precio de la renta acorde a cuántos productos se vendieron
        renta.setDateOfRent(Calendar.getInstance().getTime()); //throws an exception when it gets a date not correctly formatted
        renta.setDateOfReturn(rentaDTO.getDateOfReturn()); //throws an exception when it gets a date not correctly formatted, i hope
        renta.setProduct(productRepository.findOne(rentaDTO.getProductId())); //search for a product to add to this renta
        renta.setUser(userRepository.findByEmail(rentaDTO.getUserMail())); //search for a user with said email
        renta.setPrice(rentaDTO.getPrice()); //sets the price of said renta
        renta.setQuantity(rentaDTO.getQuantity()); //sets the quantity of said renta
        renta.setCustomer(customerRepository.findOne(rentaDTO.getCustomerID())); //sets the customer in this relation
        rentaRepository.save(renta); //here we save said renta as a registry
        //this is a damn trigger made with fucking java -> holy smokes, what have we done ; help
        productRepository.findOne(rentaDTO.getProductId()).setQuantity(productRepository.findOne(rentaDTO.getProductId()).getQuantity() - rentaDTO.getQuantity());
    }
}
