package com.ironhack.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckingAccountDTO {
    @NotNull(message = "Account's Balance must be specified")
    private BigDecimal balance;

    @NotNull(message = "Account's Currency must be specified")
    private Currency currency;

    @NotNull(message = "Account's Primary Owner must be specified")
    private Integer primaryOwner;

    private Integer secondaryOwner;

    @NotNull(message = "Account's Secret Key must be specified")
    private String secretKey;
}
