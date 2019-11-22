package com.chumbok.pos.repository;

import com.chumbok.pos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT SUM(quantiy) FROM Stock WHERE product_Id = ? order by vendor")
    Long totalQuantityInStock(Long productId);

    /**
     * I'm sorry, mom, but, at this point... who gives a fuck?
     *
     * @param date1 of when to start looking
     * @param date2 of when to end looking
     * @return the count of modifications in this period
     */
    @Query("select count (s) from Stock s where s.stockEntryDate between date1 and date2")
    Long totalAltasBetweenThisDates(Date date1, Date date2);

}