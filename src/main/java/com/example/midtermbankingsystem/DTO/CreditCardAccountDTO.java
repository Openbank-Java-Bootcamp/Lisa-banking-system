package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardAccountDTO {
    private Money balance;
    private Integer primaryOwner;
    private Integer secondaryOwner;
    private Money creditLimit;
    private BigDecimal interestRate;
    private Instant dateInterestAdded;
}
