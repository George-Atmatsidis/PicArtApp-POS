package com.chumbok.pos.repository;

import com.chumbok.pos.entity.User;
import com.chumbok.pos.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Venta findByUser(User user);
}
