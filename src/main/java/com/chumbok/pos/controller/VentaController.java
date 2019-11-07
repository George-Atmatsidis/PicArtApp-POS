package com.chumbok.pos.controller;

import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VentaController {


    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/productSalePagination", method = RequestMethod.GET)
    public ModelAndView productPageable(Pageable pageable) { //Page<Product>
        ModelAndView modelAndView = new ModelAndView();
        //Page<Product> productList = productService.findAllByPage(pageable); //why are we still here
        Page<ProductWithStockQuantity> pageProductListWithStockQuantity = productService.findProductWithStockQuantityByPage(pageable);
        modelAndView.addObject("page", pageProductListWithStockQuantity);
        modelAndView.addObject("pageable", pageable);
        modelAndView.setViewName("productSalePagination");
        return modelAndView;
    }
}
