package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardAccountDTO {
    @NotNull(message = "Account's Balance must be specified")
    private BigDecimal balance;

    @NotNull(message = "Account's Currency must be specified")
    private Currency currency;

    @NotNull(message = "Account's Primary Owner must be specified")
    private Integer primaryOwner;

    private Integer secondaryOwner;

    @NotNull(message = "Account's Secret Key must be specified")
    private String secretKey;

    @DecimalMax(value = "100000", message = "Credit Card Account's credit limit must be below 100000")
    @DecimalMin(value = "100", message = "Credit Card Account's credit limit must be above 100")
    private BigDecimal creditLimit;

    @DecimalMax(value = "0.2", message = "Credit Card Account's interest rate must be below 0.2")
    @DecimalMin(value = "0.1", message = "Credit Card Account's interest rate must be above 0.1")
    private BigDecimal interestRate;
}
