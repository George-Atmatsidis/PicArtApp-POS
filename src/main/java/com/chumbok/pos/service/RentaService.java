package com.chumbok.pos.service;

import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.entity.Renta;

public interface RentaService {

    void createRenta(RentaDTO rentaDTO) throws Exception;
}
