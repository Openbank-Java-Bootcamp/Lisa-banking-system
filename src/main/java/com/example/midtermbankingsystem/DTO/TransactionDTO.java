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

    private Integer payerAccId;

    private Integer targetAccId;

    @NotNull(message = "Transaction Target Account Holder Name must be specified")
    private String targetName;

    private String subject;

    private String secretKey;

    private Integer thirdPartyAccount;
}
