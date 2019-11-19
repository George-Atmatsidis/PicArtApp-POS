package com.chumbok.pos.controller;

import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.entity.Renta;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RentaController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RentaService rentaService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    /**
     * Controller to get /productRentPagination as a route
     *
     * @param pageable to add the products
     * @return view with the products and so
     */
    @RequestMapping(value = "/productRentPagination", method = RequestMethod.GET)
    public ModelAndView productPageable(Pageable pageable) { //Page<Product>
        ModelAndView modelAndView = new ModelAndView();
        Page<ProductWithStockQuantity> pageProductListWithStockQuantity = productService.findProductWithStockQuantityByPageGraterThanZero(pageable);
        modelAndView.addObject("page", pageProductListWithStockQuantity);
        modelAndView.addObject("pageable", pageable);
        modelAndView.setViewName("productRentPagination");
        return modelAndView;
    }

    /**
     * Método para el mapeo de /addRenta que recibe una renta y decide cómo llenar los campos
     * con base en la información obtenida en el método GET.
     *
     * @param productId which is the id of the product we are working with
     * @return the view with said objects injected
     */
    @RequestMapping(path = "/addRentas", method = RequestMethod.GET)
    public ModelAndView showAddVentasForm(@RequestParam(required = false) Long productId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Customer> customerList = customerService.findAll();
        RentaDTO rentaDTO = new RentaDTO();
        rentaDTO.setDateOfRent("" + Calendar.getInstance().getTime());
        rentaDTO.setUserMail(auth.getName());
        //Agrega una lista de clientes para escoger desde ahí
        modelAndView.addObject("customerList", customerList);
        if (productId != null) {
            //establece la cantidad máxima que se puede rentar de ese producto
            rentaDTO.setMaxQuantity(productService.getProduct(productId).getQuantity());
            rentaDTO.setProductId(productId);
            rentaDTO.setPrice(productService.getProduct(productId).getBarcode()); //sets unitary price
            rentaDTO.setUserMail(auth.getName()); //establecer el nombre del usuario en login
            rentaDTO.setProductName(productService.getProduct(productId).getDisplayName());
            modelAndView.addObject("rentaDTO", rentaDTO);
        } else {
            modelAndView.addObject("rentaDTO", new RentaDTO());
        }
        modelAndView.setViewName("addRentas");
        return modelAndView;
    }

    /**
     * You know, POST method for that shit.
     *
     * @param rentaDTO the data transfer object for data exchange
     * @return modelAndView , an html view with the Venta Object
     * @throws Exception when it doesn't get the correct data
     */
    @RequestMapping(path = "/addRentas", method = RequestMethod.POST)
    public ModelAndView createUpdateVentas(@Valid RentaDTO rentaDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        rentaDTO.setUserMail(auth.getName());
        rentaDTO.setQuantity(Math.abs(rentaDTO.getQuantity())); //ensure that only positive numbers are being inserted
        rentaService.createRenta(rentaDTO);
        modelAndView.addObject("successMessage", "Renta registrada exitosamente.");
        modelAndView.addObject("renta", rentaDTO);
        modelAndView.setViewName("addRentas");
        return modelAndView;
    }

    /**
     * Controller to get /productRentPagination as a route
     *
     * @param pageable to add the products
     * @return view with the products and so
     */
    @RequestMapping(value = "/rentList", method = RequestMethod.GET)
    public ModelAndView rentaPageable(Pageable pageable) { //Page<Product>
        ModelAndView modelAndView = new ModelAndView();
        Page<Renta> pageWithActiveRentas = rentaService.findRentasWithActiveStatus(pageable);
        modelAndView.addObject("page", pageWithActiveRentas);
        modelAndView.addObject("pageable", pageable);
        modelAndView.setViewName("rentList");
        return modelAndView;
    }

    /**
     * Gets products paginated in pages
     *
     * @param page to get (1, 2, 3, etc)
     * @return said page filled with products, 5 in each
     */
    @RequestMapping(value = "/activeRentsByPage/page/{page}")
    public ModelAndView listProductsByPage(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("rentList"); //omg, you can set the viewName at birth
        PageRequest pageable = new PageRequest(page - 1, 5); //this shit is deprecated, why the heck is it working
        Page<Renta> rentPage = rentaService.getPaginatedRents(pageable); //Why are we still here?
        int totalPages = rentPage.getTotalPages(); //Just to suffer? Every night i can feel my leg...
        if (totalPages > 0) { //and my arm... even my fingers. The body I’ve lost… the comrades I’ve lost… won't stop hurting.
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()); //It's like
            modelAndView.addObject("pageNumbers", pageNumbers); //They are all still here. You feel it, too, don't you?
        } //I'm gonna make them give back our past!
        modelAndView.addObject("activeRentList", true); //-Kazuhira Miller, Metal Gear Solid
        modelAndView.addObject("rentList", rentPage.getContent()); //Why do we even keep going?
        return modelAndView; //Is existence itself worth it?
    }
}
