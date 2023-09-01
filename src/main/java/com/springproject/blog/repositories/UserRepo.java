package com.springproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.blog.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String Email);

    //we have all stuffs such as findById,FindByemail,FindByname everything
    //in Jpa Repository so we jsut need to implement the JpaRepoditory.
}
