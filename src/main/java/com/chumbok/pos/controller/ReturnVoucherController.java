package com.chumbok.pos.controller;

import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.dto.ReturnVoucherDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.entity.Renta;
import com.chumbok.pos.entity.ReturnVoucher;
import com.chumbok.pos.repository.CustomerRepository;
import com.chumbok.pos.repository.ProductRepository;
import com.chumbok.pos.repository.RentaRepository;
import com.chumbok.pos.repository.ReturnVoucherRepository;
import com.chumbok.pos.utility.DateConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Controller
public class ReturnVoucherController {

    @Autowired
    RentaRepository rentaRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReturnVoucherRepository returnVoucherRepository;

    @Autowired
    ProductRepository productRepository;


    /**
     * This method is to manage returns of said renta
     *
     * @param rentaId of the renta to modify
     * @return a view with said data
     */
    @RequestMapping(path = "/rent/return", method = RequestMethod.GET)
    public ModelAndView showReturnRentaForm(@RequestParam(required = false) Long rentaId) {
        ModelAndView modelAndView = new ModelAndView();
        if (rentaId != null) {
            LocalDateTime now = LocalDateTime.now(); //trying to get today's date
            Date today = new Date();  //gonna save today's date here
            try { //trying to get today's date on a Date type
                today = DateConversion.stringToDate("" + now); //holy smokes
            } catch (Exception e) {
                e.printStackTrace();
            }
            Renta rentaARevisar = rentaRepository.findOne(rentaId);
            Customer customerOnTheRent = customerRepository.findOne(rentaARevisar.getCustomer().getId());
            ReturnVoucherDTO returnVoucherDTO = new ReturnVoucherDTO();
            //accede a la información almacenada en la venta para determinar el nombre del cliente
            returnVoucherDTO.setCustomerName(rentaARevisar.getCustomer().getFirstName() + " " + rentaARevisar.getCustomer().getLastName());
            returnVoucherDTO.setDateOfRent("" + rentaARevisar.getDateOfRent()); //establece la fecha en que se rentó
            returnVoucherDTO.setDateOfReturn("" + rentaARevisar.getDateOfReturn()); //establece la fecha en que se debía regresar
            returnVoucherDTO.setProductName(rentaARevisar.getProduct().getDisplayName()); //establece el nombre del product
            returnVoucherDTO.setQuantity(rentaARevisar.getQuantity()); //establece la cantidad rentada
            if (rentaARevisar.getDateOfReturn().compareTo(today) > 0) { //revisa que la fecha de entrega no sea mayor a la fecha de hoy
                returnVoucherDTO.setStatus("Devolución fuera de tiempo.");
            } else { //si la fecha de devolución es menor o igual a hoy, es una devolución válida
                returnVoucherDTO.setStatus("Devolución válida.");
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            returnVoucherDTO.setUserMakingTheReturn(auth.getName());
            returnVoucherDTO.setUserWhoMadeTheRent(rentaARevisar.getUser().getEmail());
            returnVoucherDTO.setTotalPrice(rentaARevisar.getPrice());
            modelAndView.addObject("returnVoucherDTO", returnVoucherDTO);
        }
        modelAndView.setViewName("viewRenta");
        return modelAndView;
    }

    @RequestMapping(path = "/rent/return", method = RequestMethod.POST)
    public ModelAndView makeReturnOfRenta(ReturnVoucherDTO returnVoucherDTO) {
        ModelAndView modelAndView = new ModelAndView();
        ReturnVoucher returnVoucher = new ReturnVoucher();
        //sets return date as today
        returnVoucher.setDateWhenTheReturnWasMade(Calendar.getInstance().getTime());
        //sets which rent the return is made for
        returnVoucher.setRenta(rentaRepository.findOne(returnVoucherDTO.getIdRenta()));
        //sets the commentary
        returnVoucher.setComentary(returnVoucherDTO.getComments());
        //guardar la devolución
        returnVoucherRepository.save(returnVoucher);
        //establecer la cantidad en stock
        Product product = productRepository.findOne(returnVoucher.getRenta().getProduct().getId());
        return modelAndView;
    }
}
