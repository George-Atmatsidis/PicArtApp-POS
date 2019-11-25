package com.chumbok.pos.controller;

import com.chumbok.pos.dto.PagesDTO;
import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.dto.UserSalesDTO;
import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.VentaService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class VentaController {

    @Autowired
    private ProductService productService;

    @Autowired
    private VentaService ventaService;

    /**
     * Método para el mapeo de /addVentas que recibe una venta y decide cómo llenar los campos
     * con base en la información obtenida en el método GET.
     *
     * @param productId of the product on sale
     * @return a view with the info needed
     * @throws Exception in case of.. idk
     */
    @RequestMapping(path = "/addVentas", method = RequestMethod.GET)
    public ModelAndView showAddVentasForm(@RequestParam(required = false) Long productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        VentaDTO ventaDTO = new VentaDTO();
        if (productId != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Calendar today = Calendar.getInstance();
            //establece la cantidad máxima de este producto que se puede vender
            ventaDTO.setUnitaryPrice(productService.getProduct(productId).getWeight());
            ventaDTO.setMaxQuantity(productService.getProduct(productId).getQuantity());
            ventaDTO.setEmail(auth.getName());
            ventaDTO.setProductId(productService.getProduct(productId).getId());
            ventaDTO.setSalesDate(today.getTime());
            ventaDTO.setDisplayName(productService.getProduct(productId).getDisplayName());
            modelAndView.addObject("ventaDTO", ventaDTO);
        } else {
            modelAndView.addObject("ventaDTO", new VentaDTO());
        }
        modelAndView.setViewName("addVentas");
        return modelAndView;
    }

    /**
     * You know, POST method for that shit.
     *
     * @param ventaDTO the data transfer object for data exchange
     * @return modelAndView , an html view with the Venta Object
     * @throws Exception when it doesn't get the correct data
     */
    @RequestMapping(path = "/addVentas", method = RequestMethod.POST)
    public ModelAndView createUpdateVentas(VentaDTO ventaDTO) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ventaDTO.setQuantity(Math.abs(ventaDTO.getQuantity())); //ensure that only positive numbers are being inserted
        ventaDTO.setSalesDate(Calendar.getInstance().getTime()); //ensures that today's date is when the sale is being made
        ventaDTO.setEmail(auth.getName());
        ventaService.createVenta(ventaDTO);
        Product product = productService.getProduct(ventaDTO.getProductId());
        product.setQuantity(product.getQuantity() - ventaDTO.getQuantity());
        modelAndView.addObject("successMessage", "Venta registrada exitosamente.");
        modelAndView.addObject("ventaDTO", ventaDTO);
        modelAndView.setViewName("addVentas");
        return modelAndView;
    }

    @RequestMapping(value = "/ventaProductos/pagina/{page}")
    public ModelAndView listProductsByPage(@PathVariable("page") int page, @RequestParam(required = false) Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView("productListSale"); //omg, you can set the viewName at birth
        List<Product> productList = productService.findAllEnabledWithStock();
        List<Product> justTheProductInSaidPage;
        //Let's keep the list size on 5
        int listSize = 6; //a variable, just in case :) jaj
        if (pageSize != null) {
            listSize = pageSize;
        }
        if (page == 1) {
            int i = 0;
            justTheProductInSaidPage = new ArrayList<>();
            while (i < productList.size() && i < listSize) {
                justTheProductInSaidPage.add(productList.get(i));
                i++;
            }
        } else {
            justTheProductInSaidPage = new ArrayList<>();
            int i = (page - 1) * listSize;
            int fin = i + listSize;
            while (i < productList.size() && i < fin) {
                justTheProductInSaidPage.add(productList.get(i));
                i++;
            }
        }
        int totalPages = productList.size() / listSize;
        if (productList.size() % listSize > 0) {
            totalPages += 1;
        }
        List<PagesDTO> listaDePaginas = new ArrayList<>();
        if (page > 1) {
            listaDePaginas.add(new PagesDTO("Anterior", page - 1));
        }
        if (page < totalPages) {
            listaDePaginas.add(new PagesDTO("Siguiente", page + 1));
        }
        modelAndView.addObject("listaDePaginas", listaDePaginas);
        modelAndView.addObject("products", justTheProductInSaidPage);
        return modelAndView; //Is existence itself worth it?
    }

}
