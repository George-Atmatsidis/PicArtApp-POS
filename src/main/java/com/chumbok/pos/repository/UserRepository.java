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
}