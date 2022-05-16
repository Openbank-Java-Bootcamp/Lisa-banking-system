package com.example.midtermbankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SavingsAccount extends Account{
    private String secretKey;

    private Money minimumBalance;

    private Integer interestRate;

    private Instant dateInterestAdded;
}
