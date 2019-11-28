package com.chumbok.pos.controller;

import com.chumbok.pos.dto.*;
import com.chumbok.pos.entity.Stock;
import com.chumbok.pos.service.ProductService;
import com.chumbok.pos.service.RentaService;
import com.chumbok.pos.service.StockService;
import com.chumbok.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ReportsController {

    @Autowired
    ProductService productService;

    @Autowired
    StockService stockService;

    @Autowired
    VentaService ventaService;

    @Autowired
    RentaService rentaService;

    @RequestMapping(path = "/reports")
    public ModelAndView mainReportsView() {
        return new ModelAndView("reportMain");
    }
    /**
     * Método que recibe un
     * @return a view with everything inside
     */
    @PreAuthorize("hasAuthority('ADMIN')") //just admins can see the inventory report
    @RequestMapping(path = "/inventory/selectPeriod", method = RequestMethod.GET)
    public ModelAndView mainInventoryView(@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year) {
        int thisMonth;
        int thisYear;
        if (month != null && year != null) {
            thisMonth = month;
            thisYear = year;
        } else {
            thisMonth = 11;
            thisYear = Calendar.YEAR;
        }
        List<MonthDTO> monthDTOList = new ArrayList<>();
        fillMonths(monthDTOList);
        DateDTO dateDTO = new DateDTO();
        dateDTO.setMonth(thisMonth);
        dateDTO.setYear(thisYear);
        ModelAndView modelAndView = new ModelAndView("inventoryMain");
        List<Stock> list = stockService.getAllStocksBetweenDates(thisMonth, thisYear);
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setMes(thisMonth);
        inventoryDTO.setAño(thisYear);
        inventoryDTO.setTotalModificationsThisMonth(list.size());
        inventoryDTO.setTotalBajasInSaidMonth(stockService.cantidadDeBajasEnUnPeriodo(thisMonth, thisYear));
        inventoryDTO.setTotalAltasSaidMonth(stockService.cantidadDeAltasEnUnPeriodo(thisMonth, thisYear));
        modelAndView.addObject("inventoryDTO", inventoryDTO);
        modelAndView.addObject("inventoryList", list);
        modelAndView.addObject("monthList", monthDTOList);
        modelAndView.addObject("dateDTO", dateDTO);
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

    @RequestMapping(path = "/ventasReport/selectPeriod", method = RequestMethod.GET)
    public ModelAndView showVentasReport(@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year) {
        ModelAndView modelAndView = new ModelAndView("ventasReport");
        //From here, we define which period is gonna be on the report
        int thisMonth;
        int thisYear;
        if (month != null && year != null) { //in case is defined, we use the values gotten
            thisMonth = month;
            thisYear = year;
        } else { //november 2019 in case they don't
            thisMonth = 11;
            thisYear = Calendar.YEAR;
        }
        List<UserSalesDTO> userSalesDTOList = ventaService.howMuchEachUserSoldThisMonth(thisMonth, thisYear);
        modelAndView.addObject("salesByUserList", userSalesDTOList);
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMonth(thisMonth);
        reportDTO.setYear(thisYear);
        reportDTO.setTotalSalesOrRentsThisMonth(ventaService.ventasTotalesPorMes(thisMonth, thisYear));
        reportDTO.setHowMuchMoneyWasMadeThatMonth(ventaService.howMuchWasSold(thisMonth, thisYear));
        String[] s = whoMadeTheMost(userSalesDTOList).split(",");
        reportDTO.setUserWhoSoldOrRentTheMost(s[0]); //holy smokes, is it working, i really hope it don't
        reportDTO.setHowMuchThatMadafackerSoldOrRentThatMonth(Long.parseLong(s[1])); //plZ stop
        modelAndView.addObject("reportDTO", reportDTO);
        List<MonthDTO> monthDTOList = new ArrayList<>();
        fillMonths(monthDTOList);
        DateDTO dateDTO = new DateDTO();
        dateDTO.setMonth(thisMonth);
        dateDTO.setYear(thisYear);
        modelAndView.addObject("monthList", monthDTOList);
        modelAndView.addObject("dateDTO", dateDTO);
        return modelAndView;
    }

    @RequestMapping(path = "/rentasReport/selectPeriod", method = RequestMethod.GET)
    public ModelAndView showRentasReport(@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year) {
        ModelAndView modelAndView = new ModelAndView("rentasReport");
        //From here, we define which period is gonna be on the report
        int thisMonth;
        int thisYear;
        if (month != null && year != null) { //in case is defined, we use the values gotten
            thisMonth = month;
            thisYear = year;
        } else { //november 2019 in case they don't
            thisMonth = 11;
            thisYear = Calendar.YEAR;
        }
        List<UserSalesDTO> userSalesDTOList = rentaService.howMuchEUSTM(thisMonth, thisYear);
        modelAndView.addObject("salesByUserList", userSalesDTOList);
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMonth(thisMonth);
        reportDTO.setYear(thisYear);
        reportDTO.setTotalSalesOrRentsThisMonth(rentaService.rentasTotalesPorMes(thisMonth, thisYear));
        String[] s = whoMadeTheMost(userSalesDTOList).split(",");
        reportDTO.setUserWhoSoldOrRentTheMost(s[0]); //holy smokes, is it working, i really hope it don't
        reportDTO.setHowMuchThatMadafackerSoldOrRentThatMonth(Long.parseLong(s[1])); //plZ stop
        modelAndView.addObject("reportDTO", reportDTO);
        List<MonthDTO> monthDTOList = new ArrayList<>();
        fillMonths(monthDTOList);
        DateDTO dateDTO = new DateDTO();
        dateDTO.setMonth(thisMonth);
        dateDTO.setYear(thisYear);
        modelAndView.addObject("monthList", monthDTOList);
        modelAndView.addObject("dateDTO", dateDTO);
        return modelAndView;
    }

    private String whoMadeTheMost(List<UserSalesDTO> list) {
        if (!list.isEmpty()) {
            int i = 0;
            long max = list.get(0).getQuantity();
            String userWhoMadeTheMost = list.get(0).getName();
            while (i < list.size()) {
                if (list.get(i).getQuantity() > max) {
                    userWhoMadeTheMost = list.get(i).getName();
                    max = list.get(i).getQuantity();
                }
                i++;
            }
            return userWhoMadeTheMost + "," + max;
        }
        return "N/A" + "," + "0";
    }
}
