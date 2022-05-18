package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private BigDecimal amount;
    private Currency currency;
    private Integer payerAccId;
    private Integer targetAccId;
    private String targetName;
    private String subject;
}
