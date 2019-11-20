package com.chumbok.pos.repository;

import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.entity.Renta;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {
    // add the code needed to get renta pagination (?
    List<Renta> findAllBy(Customer customer);

    @Query("SELECT r from Renta r ")
    List<Renta> findAllActive();
}
