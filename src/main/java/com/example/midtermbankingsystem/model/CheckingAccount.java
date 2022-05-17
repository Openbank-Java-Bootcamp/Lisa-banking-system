package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CheckingAccount extends Account {

    private String secretKey;

    @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance"))
    @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    @Embedded
    private Money minimumBalance;

    @AttributeOverride(name = "amount", column = @Column(name = "monthly_maitenance_fee"))
    @AttributeOverride(name = "currency", column = @Column(name = "monthly_maitenance_fee_currency"))
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
