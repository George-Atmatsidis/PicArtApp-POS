package com.chumbok.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chumbok.pos.entity.ReturnVoucher;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnVoucherRepository extends JpaRepository<ReturnVoucher, Long> {
}
