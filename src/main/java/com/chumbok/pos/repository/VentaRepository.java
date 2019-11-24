package com.chumbok.pos.repository;

import com.chumbok.pos.dto.UserSalesDTO;
import com.chumbok.pos.entity.User;
import com.chumbok.pos.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Venta findByUser(User user);

    @Query("SELECT count(v) from Venta v where (MONTH(sales_date) = ?1) and (YEAR(sales_date) = ?2)")
    long ventasTotalesPorMes(int month, int year);

    @Query("SELECT sum(v.quantity) from Venta v where (MONTH(sales_date) = ?1) and (YEAR(sales_date) = ?2)")
    long cantidadTotalDeProductosVendidosPorMes(int month, int year);

    @Query("SELECT v from Venta v where (MONTH(sales_date) = ?1) and (YEAR(sales_date) = ?2)")
    List<Venta> getVentasEnElMes(int month, int year);

    @Query("select count(v.id) from Venta v where v.user = ?1 group by v.user")
    long quantityOfVentasByUser(long idUser);

    /**
     * Filters a request to the database and gets the quantity of items sold by each user
     *
     * @param month to limit the request on
     * @param year  to limit the request on
     * @return a list with them users and so
     */
    @Query("select new com.chumbok.pos.dto.UserSalesDTO(v.user.id , CONCAT(v.user.firstName , ' ' ,  v.user.lastName) , count(v.salesDate)) from Venta v where (MONTH(v.salesDate) = ?1) and (YEAR(v.salesDate)  = ?2) group by v.user")
    List<UserSalesDTO> howMuchEachUserSoldThisMonth(int month, int year);
}
