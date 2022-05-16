package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CheckingAccount extends Account{

    private String secretKey;

    @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance"))
    @Embedded
    private Money minimumBalance;

    @AttributeOverride(name = "amount", column = @Column(name = "monthly_maitenance_fee"))
    @Embedded
    private Money monthlyMaintenanceFee;

    public CheckingAccount(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Instant creationDate,
                           Status status, String secretKey, Money minimumBalance, Money monthlyMaintenanceFee) {
        super(balance, primaryOwner, secondaryOwner, creationDate, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public CheckingAccount(Money balance, AccountHolder primaryOwner, Instant creationDate,
                           Status status, String secretKey, Money minimumBalance, Money monthlyMaintenanceFee) {
        super(balance, primaryOwner, creationDate, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }
}
