package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
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
public class CreditCardAccount extends Account {

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    })
    @Embedded
    private Money creditLimit;

    @DecimalMax(value = "0.2", message = "Credit Card Account's interest rate must be below 0.2")
    @DecimalMin(value = "0.1", message = "Credit Card Account's interest rate must be above 0.1")
    @Column(precision = 32, scale = 4)
    private BigDecimal interestRate;

    private Instant dateInterestAdded;

    public CreditCardAccount(Money balance, AccountHolder primaryOwner, Money creditLimit, BigDecimal interestRate
            , Instant dateInterestAdded) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.dateInterestAdded = dateInterestAdded;
    }

    public static CreditCardAccount fromDTO(CreditCardAccountDTO dto, AccountHolder primary, AccountHolder secondary) {
        CreditCardAccount creditCardAccount = CreditCardAccount.fromDTO(dto, primary);
        creditCardAccount.setSecondaryOwner(secondary);
        return creditCardAccount;
    }


    public static CreditCardAccount fromDTO(CreditCardAccountDTO dto, AccountHolder primary) {
        return new CreditCardAccount(dto.getBalance(), primary, dto.getCreditLimit(), dto.getInterestRate()
                , dto.getDateInterestAdded());
    }
}
