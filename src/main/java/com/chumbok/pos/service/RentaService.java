package com.chumbok.pos.service;

import com.chumbok.pos.dto.RentaDTO;
import com.chumbok.pos.entity.Renta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RentaService {

    void createRenta(RentaDTO rentaDTO) throws Exception;

    List<Renta> getActiveRentas();

    Page<Renta> getPaginatedRents(Pageable pageable);

    Page<Renta> findRentasWithActiveStatus(Pageable pageable);
}
