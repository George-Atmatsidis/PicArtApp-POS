package com.chumbok.pos.controller;

import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VentaController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/productSalePagination", method = RequestMethod.GET)
    public ModelAndView productPageable(Pageable pageable) { //Page<Product>
        ModelAndView modelAndView = new ModelAndView();
        Page<ProductWithStockQuantity> pageProductListWithStockQuantity = productService.findProductWithStockQuantityByPage(pageable);
        modelAndView.addObject("page", pageProductListWithStockQuantity);
        modelAndView.addObject("pageable", pageable);
        modelAndView.setViewName("productSalePagination");
        return modelAndView;
    }

    /**
     * Método para el mapeo de /addVentas que recibe una venta y decide cómo llenar los campos
     * con base en la información obtenida en el método GET.
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/addVentas", method = RequestMethod.GET)
    public ModelAndView showAddVentasForm(@RequestParam(required = false) Long productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Product temporalProduct = productService.getProduct(productId);
        if (productId != null) {
            VentaDTO ventaDTO = new VentaDTO();
            ventaDTO.setIdProduct(productId);
            ventaDTO.setDisplayName(temporalProduct.getDisplayName());
            modelAndView.addObject("ventaDTO", ventaDTO);
        } else {
            modelAndView.addObject("ventaDTO", new VentaDTO());
        }
        modelAndView.setViewName("addVentas");
        return modelAndView;
    }
}
