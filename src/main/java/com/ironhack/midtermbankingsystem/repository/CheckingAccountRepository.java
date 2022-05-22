package com.ironhack.midtermbankingsystem.repository;

import com.ironhack.midtermbankingsystem.model.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Integer> {
}
