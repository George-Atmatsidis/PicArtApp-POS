package com.chumbok.pos.service;

import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.dto.UserSalesDTO;
import com.chumbok.pos.entity.Renta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RentaService {

    void createRenta(RentaDTO rentaDTO);

    List<Renta> findAll();

    List<Renta> getDelayedRentas();

    List<Renta> getInTimeRentas();

    List<Renta> getActiveRentas();

    List<Renta> getNonActiveRentas();

    List<UserSalesDTO> howMuchEUSTM(int month, int year);

    long rentasTotalesPorMes(int month, int year);

    long quantityOfVentasByUser(long userid);
}
