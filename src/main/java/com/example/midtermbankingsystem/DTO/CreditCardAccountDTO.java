package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardAccountDTO {

    private BigDecimal balance;
    private Currency currency;
    private Integer primaryOwner;
    private Integer secondaryOwner;

    @DecimalMax(value = "100000", message = "Credit Card Account's credit limit must be below 100000")
    @DecimalMin(value = "100", message = "Credit Card Account's credit limit must be above 100")
    private BigDecimal creditLimit;

    @DecimalMax(value = "0.2", message = "Credit Card Account's interest rate must be below 0.2")
    @DecimalMin(value = "0.1", message = "Credit Card Account's interest rate must be above 0.1")
    private BigDecimal interestRate;

    private Instant dateInterestAdded;
}
