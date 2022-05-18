package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SavingsAccount extends Account {

    private String secretKey;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    @Embedded
    private Money minimumBalance;

    @DecimalMax(value = "0.5", message = "Savings Account's interest rate must be below 0.5")
    @DecimalMin(value = "0", message = "Savings Account's interest rate must be above 0")
    @Column(precision = 32, scale = 4)
    private BigDecimal interestRate;

    private Instant dateInterestAdded;

    public SavingsAccount(Money balance, AccountHolder primaryOwner, String secretKey, Money minimumBalance,
                          BigDecimal interestRate, Instant dateInterestAdded) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.dateInterestAdded = dateInterestAdded;
    }

    public static SavingsAccount fromDTO(SavingsAccountDTO dto, AccountHolder primary, AccountHolder secondary) {
        SavingsAccount savingsAccount = SavingsAccount.fromDTO(dto, primary);
        savingsAccount.setSecondaryOwner(secondary);
        return savingsAccount;
    }


    public static SavingsAccount fromDTO(SavingsAccountDTO dto, AccountHolder primary) {
        return new SavingsAccount(dto.getBalance(), primary, dto.getSecretKey(), dto.getMinimumBalance(),
                dto.getInterestRate(), dto.getDateInterestAdded());
    }
}
