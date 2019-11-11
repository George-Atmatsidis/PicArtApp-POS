package com.chumbok.pos.repository;

import com.chumbok.pos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Gonna define this later
    Customer findCustomerByCurpIsLike(String curp);

    //Search for a customer by name
    Customer findCustomerByFirstNameAndLastName(String firstName, String lastName);


    //TODO define if we are using curp or name as for getting if he exist, mf
    //the idea is to just define each user on the service and then ask for the curp
    @Query("SELECT COUNT(u) > 0 FROM Customer u WHERE curp = ?")
    boolean isExist(String curp);
}
