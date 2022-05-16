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
    @Embedded
    private Money minimumBalance;

    @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    @Embedded
    private Money creditLimit;

    private Integer interestRate;

    private Instant dateInterestAdded;
}
