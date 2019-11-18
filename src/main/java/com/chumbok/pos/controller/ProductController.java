package com.chumbok.pos.controller;

import com.chumbok.pos.dto.ProductDTO;
import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @RequestMapping(path = "/product", method = RequestMethod.GET)
    public ModelAndView showAddProductForm(@RequestParam(required = false) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (id != null) {
            Product product = productService.getProduct(id);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(id);
            productDTO.setCatagory(product.getCatagory());
            productDTO.setDescription(product.getDescription());
            productDTO.setDisplayName(product.getDisplayName());
            productDTO.setRentPrice(product.getBarcode());
            productDTO.setSalesPrice(product.getWeight());
            productDTO.setVendor(product.getVendor());
            modelAndView.addObject("productDTO", productDTO);
        } else {
            modelAndView.addObject("productDTO", new ProductDTO());
        }
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @RequestMapping(path = "/product", method = RequestMethod.POST)
    public ModelAndView createUpdateProduct(@Valid ProductDTO productDTO) {
        ModelAndView modelAndView = new ModelAndView();
        if (productDTO.getId() < 0) {
            productService.createProduct(productDTO);
            modelAndView.addObject("successMessage", "El material se ha registrado exitosamente.");
            modelAndView.addObject("productDTO", new ProductDTO());
            modelAndView.setViewName("product");
        } else {
            productService.updateProduct(productDTO);
            modelAndView.addObject("successMessage", "Material actualizado correctamente.");
            modelAndView.addObject("productDTO", productDTO);
            modelAndView.setViewName("product");
        }
        return modelAndView;
    }

    /**
     * @param page
     * @return
     */
    @RequestMapping(value = "/productsByPage/page/{page}")
    public ModelAndView listProductsByPage(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("product-list-paging"); //omg, you can set the viewName at birth
        PageRequest pageable = new PageRequest(page - 1, 5); //this shit is deprecated, why the heck is it working
        Page<Product> productPage = productService.getPaginatedProducts(pageable); //Why are we still here?
        int totalPages = productPage.getTotalPages(); //Just to suffer? Every night i can feel my leg...
        if (totalPages > 0) { //and my arm... even my fingers. The body I’ve lost… the comrades I’ve lost… won't stop hurting.
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()); //It's like
            modelAndView.addObject("pageNumbers", pageNumbers); //They are all still here. You feel it, too, don't you?
        } //I'm gonna make them give back our past!
        modelAndView.addObject("activeProductList", true); //-Kazuhira Miller, Metal Gear Solid
        modelAndView.addObject("productList", productPage.getContent()); //Why do we even keep going?
        return modelAndView; //Is existence itself worth it?
    }

    @RequestMapping(value = "/pageable", method = RequestMethod.GET)
    public ModelAndView productPageable(Pageable pageable) { //Page<Product>
        ModelAndView modelAndView = new ModelAndView();
        Page<ProductWithStockQuantity> pageProductListWithStockQuantity = productService.findProductWithStockQuantityByPage(pageable);
        modelAndView.addObject("page", pageProductListWithStockQuantity);
        modelAndView.addObject("pageable", pageable);
        modelAndView.setViewName("productPagination");
        return modelAndView;
    }

    @RequestMapping(value = "/products/doDelete", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam(required = false) List<Long> ids, Long id) {
        if (ids == null) {
            productService.deleteProduct(id);
        } else {
            productService.deleteBulkProduct(ids);
        }
        return "redirect:/home";
    }
}