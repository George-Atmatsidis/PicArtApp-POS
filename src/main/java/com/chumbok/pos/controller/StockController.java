package com.chumbok.pos.controller;

import com.chumbok.pos.dto.StockDTO;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Calendar;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')") //Just admins can access to the add stock page
    @RequestMapping(path = "/addStock", method = RequestMethod.GET)
    public ModelAndView showAddStockForm(@RequestParam(required = false) Long productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockEntryDate(Calendar.getInstance().getTime());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (productId != null) {
            stockDTO.setUser(auth.getName());
            stockDTO.setProductId(productId);
            stockDTO.setProductName(productService.getProduct(productId).getDisplayName());
            modelAndView.addObject("stockDTO", stockDTO);
        } else {
            modelAndView.addObject("stockDTO", stockDTO);
        }
        modelAndView.setViewName("addStock");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')") //just admins can add stock
    @RequestMapping(path = "/addStock", method = RequestMethod.POST)
    public ModelAndView createUpdateStock(@Valid StockDTO stockDTO) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        stockDTO.setUser(auth.getName());
        stockDTO.setQuantiy(Math.abs(stockDTO.getQuantiy())); //in case they send a fucking minus, we don't care
        stockService.createStock(stockDTO);
        modelAndView.addObject("successMessage", "Inventario actualizado correctamente.");
        modelAndView.addObject("stockDTO", new StockDTO());
        modelAndView.setViewName("addStock");
        return modelAndView;
    }

    /**
     * This method is used when a stock is going to be registered as a negative one.
     * So, whenever a decrease of stock happens, it goes here.
     *
     * @param stockDTO which is the new stock type of thing
     * @return modelAndView with a clean stockDTO
     */
    @PreAuthorize("hasAuthority('ADMIN')") //just admins can delete from stock
    @RequestMapping(path = "/restStock", method = RequestMethod.POST)
    public ModelAndView deleteFromStock(@Valid StockDTO stockDTO) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        stockDTO.setUser(auth.getName());
        stockDTO.setQuantiy(Math.abs(stockDTO.getQuantiy()) * -1); //Converts the quantity to negative, i hope so
        stockService.createStock(stockDTO);
        modelAndView.addObject("successMessage", "Inventario actualizado correctamente.");
        modelAndView.addObject("stockDTO", new StockDTO());
        modelAndView.setViewName("restStock");
        return modelAndView;
    }

    /**
     * Provides the interface for the decrease of a stock.
     *
     * @param productId which is the product whose stock is going to be modified
     * @return a view from the html files containing a clean stock object
     * @throws Exception in case... of error, for now
     */
    @PreAuthorize("hasAuthority('ADMIN')") //just admins can remove from stock
    @RequestMapping(path = "/restStock", method = RequestMethod.GET)
    public ModelAndView showRestStockForm(@RequestParam(required = false) Long productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockEntryDate(Calendar.getInstance().getTime());
        stockDTO.setUser(auth.getName());
        if (productId != null) {
            stockDTO.setUser(auth.getName());
            stockDTO.setProductId(productId);
            stockDTO.setProductName(productService.getProduct(productId).getDisplayName());
            modelAndView.addObject("stockDTO", stockDTO);
        } else {
            modelAndView.addObject("stockDTO", stockDTO);
        }
        modelAndView.setViewName("restStock");
        return modelAndView;
    }
}
