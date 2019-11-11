package com.chumbok.pos.controller;

import com.chumbok.pos.dto.StockDTO;
import com.chumbok.pos.entity.Stock;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.StockService;
import com.chumbok.pos.utility.DateConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    //TODO add product name to the stockDTO

    @RequestMapping(path = "/addStock", method = RequestMethod.GET)
    public ModelAndView showAddStockForm(@RequestParam(required = false) Long productId) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        if (productId != null) {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setProductId(productId);
            modelAndView.addObject("stockDTO", stockDTO);
        } else {
            modelAndView.addObject("stockDTO", new StockDTO());
        }
        modelAndView.setViewName("addStock");
        return modelAndView;
    }

    @RequestMapping(path = "/addStock", method = RequestMethod.POST)
    public ModelAndView createUpdateStock(@Valid StockDTO stockDTO) {
        ModelAndView modelAndView = new ModelAndView();
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
        stockDTO.setQuantiy(stockDTO.getQuantiy() * -1); //Converts the quantity to negative, i hope so
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
        if (productId != null) {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setProductId(productId);
            modelAndView.addObject("stockDTO", stockDTO);
        } else {
            modelAndView.addObject("stockDTO", new StockDTO());
        }
        modelAndView.setViewName("restStock");
        return modelAndView;
    }

    /*@ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Stock> getStocks() {
        List<Stock> list = stockService.getAllStocks();
        return list;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public PersistedObjId createStock(@RequestBody @Valid StockDTO stockDTO) {

        Stock stock = stockService.createStock(stockDTO);
        return new PersistedObjId(stock.getId());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Stock getStockById(@PathVariable("id") Integer id) {
        Stock stock = stockService.getStock(id);
        return stock;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateStock(@PathVariable("id") Long id, @RequestBody @Valid Stock stock) {
        stock.setId(id);
        stockService.updateStock(stock);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteStock(@PathVariable("id") Long id) {
        stockService.deleteStock(id);
    }*/
}
