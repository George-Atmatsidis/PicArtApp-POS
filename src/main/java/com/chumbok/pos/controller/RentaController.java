package com.chumbok.pos.controller;

import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.entity.Renta;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    //TODO add the rest of this mf code, no me puedo concentrar, no sé qué está ocurriendo aquí

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
        User user = userService.findUserByEmail(auth.getName());
        if (productId != null) {
            RentaDTO rentaDTO = new RentaDTO();
            rentaDTO.setProductId(productId);
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
    public ModelAndView createUpdateVentas(RentaDTO rentaDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        rentaService.createRenta(rentaDTO);
        modelAndView.addObject("successMessage", "Renta registrada exitosamente.");
        modelAndView.addObject("renta", rentaDTO);
        modelAndView.setViewName("addRentas");
        return modelAndView;
    }
}
