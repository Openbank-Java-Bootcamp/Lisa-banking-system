package com.ironhack.midtermbankingsystem.repository;

import com.ironhack.midtermbankingsystem.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
}
