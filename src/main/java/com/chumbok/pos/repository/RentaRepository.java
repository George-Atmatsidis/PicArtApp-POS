package com.chumbok.pos.repository;

import com.chumbok.pos.entity.Renta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {
    // add the code needed to get renta pagination (?

    //encontrar las que tienen cierto usuario, pero creo que requiero una lista también

}
