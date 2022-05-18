package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    @NotNull(message = "Transaction amount must be specified")
    private BigDecimal amount;
    @NotNull(message = "Transaction currency must be specified")
    private Currency currency;
    @NotNull(message = "Transaction Payer Account ID must be specified")
    private Integer payerAccId;
    @NotNull(message = "Transaction Target Account ID must be specified")
    private Integer targetAccId;
    @NotNull(message = "Transaction Target Account Holder Name must be specified")
    private String targetName;

    private String subject;
}
