package com.example.midtermbankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CreditCardAccount extends Account{

    @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance"))
    @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    @Embedded
    private Money minimumBalance;

    @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    @Embedded
    private Money creditLimit;

    private Integer interestRate;

    private Instant dateInterestAdded;
}
