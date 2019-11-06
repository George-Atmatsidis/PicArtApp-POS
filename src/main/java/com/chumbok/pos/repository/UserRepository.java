package com.chumbok.pos.repository;

import com.chumbok.pos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findById(long id);

    //Accede a la lista de usuarios en busca de un usuario con dicho email registrado
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE email = ?")
    boolean isExist(String email);

    //Esto debería crear una lista de usuarios, you know
    // @Query("SELECT new com.chumbok.pos.entity.User(u.id, u.email, u.password, u.firstName, u.lastName, u.active, u.roles) FROM User u") +
    //       "GROUP BY u.id");
    //TODO create a real UserDTO for user pagination
    //https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
    //List<User> userList();
}