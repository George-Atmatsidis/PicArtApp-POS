package com.chumbok.pos.controller;

import com.chumbok.pos.dto.PagesDTO;
import com.chumbok.pos.dto.ReturnVoucherDTO;
import com.chumbok.pos.entity.Renta;
import com.chumbok.pos.entity.ReturnVoucher;
import com.chumbok.pos.repository.RentaRepository;
import com.chumbok.pos.repository.ReturnVoucherRepository;
import com.chumbok.pos.service.ReturnVoucherService;
import com.chumbok.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ReturnVoucherController {

    @Autowired
    RentaRepository rentaRepository;

    @Autowired
    ReturnVoucherRepository returnVoucherRepository;

    @Autowired
    ReturnVoucherService returnVoucherService;

    @Autowired
    UserService userService;

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
            Renta rentaARevisar = rentaRepository.findOne(rentaId);
            ReturnVoucherDTO returnVoucherDTO = new ReturnVoucherDTO();
            returnVoucherDTO.setIdRenta(rentaARevisar.getIdRenta());
            //accede a la información almacenada en la venta para determinar el nombre del cliente
            returnVoucherDTO.setCustomerName(rentaARevisar.getCustomer().getFirstName() + " " + rentaARevisar.getCustomer().getLastName());
            returnVoucherDTO.setDateOfRent("" + rentaARevisar.getDateOfRent()); //establece la fecha en que se rentó
            returnVoucherDTO.setDateOfReturn(rentaARevisar.getDateOfReturn()); //establece la fecha en que se debía regresar
            returnVoucherDTO.setProductName(rentaARevisar.getProduct().getDisplayName()); //establece el nombre del product
            returnVoucherDTO.setQuantity(rentaARevisar.getQuantity()); //establece la cantidad rentada
            if (rentaARevisar.getDateOfReturn().compareTo(Calendar.getInstance().getTime()) <= 0) { //revisa que la fecha de entrega no sea mayor a la fecha de hoy
                returnVoucherDTO.setStatus("Devolución fuera de tiempo.");
            } else { //si la fecha de devolución es menor o igual a hoy, es una devolución válida
                returnVoucherDTO.setStatus("Devolución válida.");
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            returnVoucherDTO.setUserMakingTheReturn(userService.findUserByEmail(auth.getName()).getFirstName() + ' ' + userService.findUserByEmail(auth.getName()).getLastName());//auth.getName());
            returnVoucherDTO.setUserWhoMadeTheRent(rentaARevisar.getUser().getFirstName() + ' ' + rentaARevisar.getUser().getLastName());
            returnVoucherDTO.setTotalPrice(rentaARevisar.getPrice());
            modelAndView.addObject("returnVoucherDTO", returnVoucherDTO);
        }
        modelAndView.setViewName("viewRenta");
        return modelAndView;
    }

    @RequestMapping(path = "/returnDetails", method = RequestMethod.GET)
    public ModelAndView showReturnDetails(@RequestParam(required = false) Long returnId) {
        ModelAndView modelAndView = new ModelAndView();
        if (returnId != null) {
            ReturnVoucher returnVoucher = returnVoucherService.findOne(returnId);
            Renta rentaARevisar = rentaRepository.findOne(returnVoucher.getRenta().getIdRenta());
            ReturnVoucherDTO returnVoucherDTO = new ReturnVoucherDTO();
            returnVoucherDTO.setIdRenta(rentaARevisar.getIdRenta());
            //accede a la información almacenada en la venta para determinar el nombre del cliente
            returnVoucherDTO.setCustomerName(rentaARevisar.getCustomer().getFirstName() + " " + rentaARevisar.getCustomer().getLastName());
            returnVoucherDTO.setDateOfRent("" + rentaARevisar.getDateOfRent()); //establece la fecha en que se rentó
            returnVoucherDTO.setDateOfReturn(returnVoucher.getDateWhenTheReturnWasMade());
            returnVoucherDTO.setProductName(rentaARevisar.getProduct().getDisplayName()); //establece el nombre del product
            returnVoucherDTO.setQuantity(rentaARevisar.getQuantity()); //establece la cantidad rentada
            returnVoucherDTO.setStatus(returnVoucher.getWasItMadeOnTime());
            returnVoucherDTO.setUserMakingTheReturn(returnVoucher.getUser().getFirstName() + ' ' + returnVoucher.getUser().getLastName());
            returnVoucherDTO.setUserWhoMadeTheRent(rentaARevisar.getUser().getFirstName() + ' ' + rentaARevisar.getUser().getLastName());
            returnVoucherDTO.setTotalPrice(rentaARevisar.getPrice());
            returnVoucherDTO.setComments(returnVoucher.getComentary());
            modelAndView.addObject("returnVoucherDTO", returnVoucherDTO);
        }
        modelAndView.setViewName("viewRentaVoucher");
        return modelAndView;
    }

    /**
     * Método para registrar una devolución
     *
     * @param returnVoucherDTO que es la información de la renta que se realiza.
     * @return a redirect to the last page of returns list
     */
    @RequestMapping(path = "/rent/return", method = RequestMethod.POST)
    public String makeReturnOfRenta(ReturnVoucherDTO returnVoucherDTO) {
        ModelAndView modelAndView = new ModelAndView("viewRenta");
        //sets return date as today
        returnVoucherDTO.setDateOfReturn(Calendar.getInstance().getTime());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        returnVoucherDTO.setUserMakingTheReturn(auth.getName());
        returnVoucherService.createVoucher(returnVoucherDTO);
        return "redirect:/returns/list/500";
    }

    /**
     * Método que rastrea la página a regresar con devoluciones anteriores
     *
     * @param page with the page number the user wants to get
     * @return a view with them returns
     */
    @RequestMapping(path = "returns/list/{page}", method = RequestMethod.GET)
    public ModelAndView showPagedReturns(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("returnList");
        List<ReturnVoucher> returnVouchers = returnVoucherRepository.findAll();
        List<ReturnVoucher> justTheReturnsInSaidPage;
        //Let's keep the list size on 5
        if (page == 1) {
            int i = 0;
            justTheReturnsInSaidPage = new ArrayList<>();
            while (i < returnVouchers.size() && i < 5) {
                justTheReturnsInSaidPage.add(returnVouchers.get(i));
                i++;
            }
        } else {
            justTheReturnsInSaidPage = new ArrayList<>();
            int i = (page - 1) * 5;
            int fin = i + 5;
            while (i < returnVouchers.size() && i < fin) {
                justTheReturnsInSaidPage.add(returnVouchers.get(i));
                i++;
            }
        }
        int totalPages = returnVouchers.size() / 5;
        if (returnVouchers.size() % 5 > 0) {
            totalPages += 1;
        }
        if (page > totalPages) { //return lastPage if
            return showPagedReturns(totalPages);
        }
        List<PagesDTO> listaDePaginas = new ArrayList<>();
        if (page > 1) {
            listaDePaginas.add(new PagesDTO("Anterior", page - 1));
        }
        if (page < totalPages) {
            listaDePaginas.add(new PagesDTO("Siguiente", page + 1));
        }
        modelAndView.addObject("listaDePaginas", listaDePaginas);
        modelAndView.addObject("returns", justTheReturnsInSaidPage);
        return modelAndView;
    }

    /**
     * Menú principal de devoluciones
     *
     * @return a view with them options
     */
    @RequestMapping(path = "/returns", method = RequestMethod.GET)
    public ModelAndView showMainReturnPage() {
        return new ModelAndView("returnMain");
    }
}
