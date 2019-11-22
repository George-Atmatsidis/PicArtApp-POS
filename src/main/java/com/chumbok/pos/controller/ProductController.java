package com.chumbok.pos.controller;

import com.chumbok.pos.dto.PagesDTO;
import com.chumbok.pos.dto.ProductDTO;
import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')") //Just admins can access to the modify product page
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

    @PreAuthorize("hasAuthority('ADMIN')") //Just admins can edit products
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
     * Gets products paginated in pages
     *
     * @param page to get (1, 2, 3, etc)
     * @return said page filled with products, 5 in each
     */
    @RequestMapping(value = "/productsByPage/page/{page}")
    public ModelAndView listProductsByPage(@PathVariable("page") int page, @RequestParam(required = false) Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView("productListPagination"); //omg, you can set the viewName at birth
        List<Product> productList = productService.findAll();
        List<Product> justTheProductInSaidPage;
        //Let's keep the list size on 5
        int listSize = 4; //a variable, just in case :)
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


    @RequestMapping(value = "/pageable", method = RequestMethod.GET)
    public ModelAndView productPageable(@PageableDefault(size = 5) Pageable pageable) { //Page<Product>
        ModelAndView modelAndView = new ModelAndView();
        Page<ProductWithStockQuantity> pageProductListWithStockQuantity = productService.findProductWithStockQuantityByPage(pageable);
        modelAndView.addObject("page", pageProductListWithStockQuantity);
        modelAndView.addObject("pageable", pageable);
        modelAndView.setViewName("productPagination");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')") //just admin can disable products
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