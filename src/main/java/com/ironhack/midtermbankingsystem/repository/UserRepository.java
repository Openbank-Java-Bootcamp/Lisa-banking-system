package com.ironhack.midtermbankingsystem.repository;

import com.ironhack.midtermbankingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

}
