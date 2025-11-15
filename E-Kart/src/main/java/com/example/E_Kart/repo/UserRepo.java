package com.example.E_Kart.repo;

import com.example.E_Kart.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

   Optional<User> findByUsername(String username);
}