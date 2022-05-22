package com.ironhack.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountDTO {
    @NotNull(message = "Account's Balance must be specified")
    private BigDecimal balance;

    @NotNull(message = "Account's Currency must be specified")
    private Currency currency;

    @NotNull(message = "Account's Primary Owner must be specified")
    private Integer primaryOwner;

    private Integer secondaryOwner;

    @NotNull(message = "Account's Secret Key must be specified")
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
