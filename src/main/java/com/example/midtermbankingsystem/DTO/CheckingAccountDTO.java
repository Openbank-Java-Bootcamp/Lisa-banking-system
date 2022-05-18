package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckingAccountDTO {
    private BigDecimal balance;
    private Currency currency;
    private Integer primaryOwner;
    private Integer secondaryOwner;
    private String secretKey;
}
