package com.SpringJwtMySql.SpringJwt.repository;

import com.SpringJwtMySql.SpringJwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //Extends JpaRepository, which is a Spring Data JPA interface that gives you tons of ready-made methods like: findAll(), findById(), save(), deleteById(), etc.
    //<User - the base entity type on which operations done, Integer - primary key type of User>


    //Optional value can be either User type or null
    Optional<User> findByUsername(String username);
    //This is a custom query method. Spring Data JPA generates the implementation automatically based on the method name.
    //It will execute something equivalent to:-
    //SELECT * FROM user WHERE username = ?
}