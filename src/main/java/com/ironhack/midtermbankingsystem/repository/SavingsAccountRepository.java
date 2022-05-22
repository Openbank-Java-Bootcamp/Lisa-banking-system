package com.ironhack.midtermbankingsystem.repository;

import com.ironhack.midtermbankingsystem.model.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer> {
}
