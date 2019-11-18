package com.chumbok.pos.controller;

import com.chumbok.pos.dto.StockDTO;
import com.chumbok.pos.entity.Stock;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.StockService;
import com.chumbok.pos.utility.DateConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/addStock", method = RequestMethod.GET)
    public ModelAndView showAddStockForm(@RequestParam(required = false) Long productId) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        Calendar today = Calendar.getInstance();
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockEntryDate(today.getTime());
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
    @RequestMapping(path = "/restStock", method = RequestMethod.POST)
    public ModelAndView deleteFromStock(@Valid StockDTO stockDTO) {
        ModelAndView modelAndView = new ModelAndView();
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
    @RequestMapping(path = "/restStock", method = RequestMethod.GET)
    public ModelAndView showRestStockForm(@RequestParam(required = false) Long productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Calendar today = Calendar.getInstance();
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockEntryDate(today.getTime());
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
