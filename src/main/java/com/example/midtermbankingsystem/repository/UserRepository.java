package com.example.midtermbankingsystem.repository;

import com.example.midtermbankingsystem.enums.Role;
import com.example.midtermbankingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

}
