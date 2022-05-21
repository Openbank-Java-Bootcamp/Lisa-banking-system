package com.example.midtermbankingsystem.repository;

import com.example.midtermbankingsystem.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Integer> {

    Optional<ThirdParty> findByThirdPartyAccount(Integer thirdPartyAccount);
}
