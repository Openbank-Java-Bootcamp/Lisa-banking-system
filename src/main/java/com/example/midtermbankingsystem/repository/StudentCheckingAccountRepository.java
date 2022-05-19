package com.example.midtermbankingsystem.repository;

import com.example.midtermbankingsystem.model.StudentCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingAccountRepository extends JpaRepository<StudentCheckingAccount, Integer> {
}
