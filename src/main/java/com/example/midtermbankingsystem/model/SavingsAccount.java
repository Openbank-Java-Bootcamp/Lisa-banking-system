package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.example.midtermbankingsystem.DTO.StudentCheckingAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

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
    @Column(precision = 32, scale = 4)
    private Money minimumBalance;

    @Column(precision = 32, scale = 4)
    private BigDecimal interestRate;

    @CreationTimestamp
    private LocalDate dateInterestAdded;

    public SavingsAccount(Money balance, AccountHolder primaryOwner, String secretKey, Money minimumBalance,
                          BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public static SavingsAccount fromDTO(SavingsAccountDTO dto, AccountHolder primary, AccountHolder secondary) {
        SavingsAccount savingsAccount = SavingsAccount.fromDTO(dto, primary);
        savingsAccount.setSecondaryOwner(secondary);
        return savingsAccount;
    }


    public static SavingsAccount fromDTO(SavingsAccountDTO dto, AccountHolder primary) {

        var interestR = dto.getInterestRate() != null ? dto.getInterestRate() : new BigDecimal(0.0025);
        var minB = dto.getMinimumBalance() != null ? dto.getMinimumBalance() : new BigDecimal(1000);

        return new SavingsAccount(new Money(dto.getBalance(), dto.getCurrency()), primary, dto.getSecretKey()
                , new Money(minB, dto.getCurrency()), interestR);
    }
}
