package com.chumbok.pos.controller;

import com.chumbok.pos.dto.InventoryDTO;
import com.chumbok.pos.dto.MonthDTO;
import com.chumbok.pos.entity.Stock;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequestMapping(path = "/inventory")
@Controller
public class InventoryController {

    @Autowired
    ProductService productService;

    @Autowired
    StockService stockService;

    /**
     * Método que recibe un
     *
     * @param inventoryDTO with the dates when to look for
     * @return a view with everything inside
     */
    @RequestMapping(path = "/byMonth", method = RequestMethod.GET)
    public ModelAndView mainInventoryView(InventoryDTO inventoryDTO) {
        List<MonthDTO> monthDTOList = new ArrayList<>();
        fillMonths(monthDTOList);
        ModelAndView modelAndView = new ModelAndView();
        List<Stock> list = stockService.getAllStocksBetweenDates(inventoryDTO.getMes(), inventoryDTO.getAño());
        inventoryDTO.setTotalBajasInSaidMonth(stockService.cantidadDeBajasEnUnPeriodo(inventoryDTO.getMes(), inventoryDTO.getAño()));
        inventoryDTO.setTotalAltasSaidMonth(stockService.cantidadDeAltasEnUnPeriodo(inventoryDTO.getMes(), inventoryDTO.getAño()));
        inventoryDTO.setTotalModificationsThisMonth(list.size());
        modelAndView.addObject("monthList", monthDTOList);
        modelAndView.addObject("stockList", list);
        modelAndView.addObject("inventoryDTO", inventoryDTO);
        return modelAndView;
    }

    @RequestMapping(path = "/currentMonth", method = RequestMethod.GET)
    public ModelAndView currentMonthInventoryView() {
        ModelAndView modelAndView = new ModelAndView();
        InventoryDTO inventoryDTO = new InventoryDTO();
        List<Stock> list = new ArrayList<>();
        modelAndView.addObject("stockList", list);
        modelAndView.addObject("inventoryDTO", inventoryDTO);
        return modelAndView;
    }

    private void fillMonths(List<MonthDTO> monthDTOList) {
        monthDTOList.add(new MonthDTO(1, "Enero"));
        monthDTOList.add(new MonthDTO(2, "Febrero"));
        monthDTOList.add(new MonthDTO(3, "Marzo"));
        monthDTOList.add(new MonthDTO(4, "Abril"));
        monthDTOList.add(new MonthDTO(5, "Mayo"));
        monthDTOList.add(new MonthDTO(6, "Junio"));
        monthDTOList.add(new MonthDTO(7, "Julio"));
        monthDTOList.add(new MonthDTO(8, "Agosto"));
        monthDTOList.add(new MonthDTO(9, "Septiembre"));
        monthDTOList.add(new MonthDTO(10, "Octubre"));
        monthDTOList.add(new MonthDTO(11, "Noviembre"));
        monthDTOList.add(new MonthDTO(12, "Diciembre"));
    }
}