package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.model.Money;
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
public class SavingsAccountDTO {

    private BigDecimal balance;
    private Currency currency;
    private Integer primaryOwner;
    private Integer secondaryOwner;
    private String secretKey;

    @DecimalMax(value = "1000", message = "Savings Account' minimum balance must be below 1000")
    @DecimalMin(value = "100", message = "Savings Account's minimum balance must be above 100")
    @Column(precision = 32, scale = 4)
    private BigDecimal minimumBalance;

    @DecimalMax(value = "0.5", message = "Savings Account's interest rate must be below 0.5")
    @DecimalMin(value = "0", message = "Savings Account's interest rate must be above 0")
    @Column(precision = 32, scale = 4)
    private BigDecimal interestRate;
}
