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
    @Column(precision = 32, scale = 4)
    private Money creditLimit;

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

        var interestR = dto.getInterestRate() != null ? dto.getInterestRate() : new BigDecimal(0.2);
        var creditL = dto.getCreditLimit() != null ? dto.getCreditLimit() : new BigDecimal(100);

        return new CreditCardAccount(new Money(dto.getBalance(), dto.getCurrency()), primary
                , new Money(creditL, dto.getCurrency()), interestR, dto.getDateInterestAdded());
    }
}
