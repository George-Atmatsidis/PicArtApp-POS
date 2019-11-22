package com.chumbok.pos.repository;

import com.chumbok.pos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT SUM(quantiy) FROM Stock WHERE product_Id = ?")
    Long totalQuantityInStock(Long productId);

    /**
     * I'm sorry, mom, but, at this point... who gives a fuck?
     *
     * @param month of when to start looking
     * @param year of when to end looking
     * @return the count of modifications in this period
     */
    @Query("select count(s) from Stock s where MONTH(s.stockEntryDate) = ?1 and (YEAR(stock_entry_date) = ?2) and quantiy > 0")
    Long totalAltasByMonthAndYear(int month, int year);

    /**
     * I'm sorry, mom, but, at this point... who gives a fuck?
     *
     * @param month of when to start looking
     * @param year  of when to end looking
     * @return the count of modifications in this period
     */
    @Query("select count(s) from Stock s where MONTH(s.stockEntryDate) = ?1 and (YEAR(stock_entry_date) = ?2) and quantiy < 0")
    Long totalBajasByMonthAndYear(int month, int year);

    /**
     * Gets a list with the stock from between two dates
     *
     * @param month when to start looking
     * @param year  when to start looking
     * @return a list with said modifications
     */
    @Query("SELECT s from Stock s where (MONTH(s.stockEntryDate) = ?1) and (YEAR(stock_entry_date) = ?2)")
    List<Stock> findAllByStockEntryDateByMonthAndYear(int month, int year);
}