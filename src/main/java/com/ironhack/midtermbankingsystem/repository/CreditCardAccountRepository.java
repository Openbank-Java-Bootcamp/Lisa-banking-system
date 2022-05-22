package com.ironhack.midtermbankingsystem.repository;

import com.ironhack.midtermbankingsystem.model.CreditCardAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Integer> {
}
