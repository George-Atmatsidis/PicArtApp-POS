package com.chumbok.pos.repository;

import com.chumbok.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Gonna define this later
    Customer findCustomerByCurpIsLike(String curp);

    //Holy smokes, we are spitting fire like is hot sauce
    Customer findCustomerByCurp(String curp);

    //Search for a customer by full name
    Customer findCustomerByFirstNameAndLastName(String firstName, String lastName);

    //the idea is to just define each user on the service and then ask for the curp
    @Query("SELECT COUNT(u) > 0 FROM Customer u WHERE curp = ?")
    boolean isExist(String curp);
}
