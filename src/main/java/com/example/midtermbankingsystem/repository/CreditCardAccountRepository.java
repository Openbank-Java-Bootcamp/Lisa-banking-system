package com.example.midtermbankingsystem.repository;

import com.example.midtermbankingsystem.model.CreditCardAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Integer> {
}
