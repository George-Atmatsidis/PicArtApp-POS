package com.chumbok.pos.repository;

import com.chumbok.pos.dto.UserSalesDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.entity.Renta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {
    // add the code needed to get renta pagination (?
    List<Renta> findAllBy(Customer customer);

    @Query("SELECT r from Renta r ")
    List<Renta> findAllActive();

    @Query("select count(r) from Renta r where (MONTH(date_of_rent) = ?1 ) and (YEAR(date_of_rent) = ?2)")
    long quantityOfRentasMadeByMonthAndYear(int month, int year);

    @Query("select count(r.id) from Renta r where r.user = ?1 group by r.user")
    long quantityOfRentasByUser(long idUser);

    @Query("select r from Renta r where (MONTH(date_of_rent) = ?1 ) and (YEAR(date_of_rent) = ?2)")
    List<Renta> readRentasByMonthAndYear(int month, int year);

    @Query("select new com.chumbok.pos.dto.UserSalesDTO(r.user.id , CONCAT(r.user.firstName , ' ' ,  r.user.lastName) , count(r.dateOfRent)) from Renta r where (MONTH(r.dateOfRent) = ?1) and (YEAR(r.dateOfRent)  = ?2) group by r.user")
    List<UserSalesDTO> howMuchEachUserRentedThisMonth(int month, int year);

    @Query("SELECT count(r) from Renta r where (MONTH(date_of_rent) = ?1) and (YEAR(date_of_rent) = ?2)")
    long rentasTotalesPorMes(int month, int year);
}
