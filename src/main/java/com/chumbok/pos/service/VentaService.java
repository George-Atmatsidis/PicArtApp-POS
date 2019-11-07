package com.chumbok.pos.service;

import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Venta;

public interface VentaService {

    //Se crea una venta con base en las modificaciones realizadas al DTO
    Venta createVenta(VentaDTO ventaDTO);

}
