package com.chumbok.pos.service;

import com.chumbok.pos.dto.UserSalesDTO;
import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Venta;

import java.util.List;

public interface VentaService {

    //Se crea una venta con base en las modificaciones realizadas al DTO
    Venta createVenta(VentaDTO ventaDTO);

    long quantityOfVentasByUser(long userid);

    List<UserSalesDTO> howMuchEachUserSoldThisMonth(int month, int year);

    List<Venta> getVentasEnElMes(int month, int year);

    long ventasTotalesPorMes(int month, int year);
}
