package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CheckingAccount extends Account {

    private String secretKey;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    @Embedded
    @Column(precision = 32, scale = 4)
    private Money minimumBalance;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maitenance_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maitenance_fee_currency"))
    })
    @Embedded
    @Column(precision = 32, scale = 4)
    private Money monthlyMaintenanceFee;

    public CheckingAccount(Money balance, AccountHolder primaryOwner, String secretKey, Money minimumBalance,
                           Money monthlyMaintenanceFee) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }


    public static CheckingAccount fromDTO(CheckingAccountDTO dto, AccountHolder primary, AccountHolder secondary) {
        CheckingAccount checkingAccount = CheckingAccount.fromDTO(dto, primary);
        checkingAccount.setSecondaryOwner(secondary);
        return checkingAccount;
    }

    public static CheckingAccount fromDTO(CheckingAccountDTO dto, AccountHolder primary) {
        return new CheckingAccount(new Money(dto.getBalance(), dto.getCurrency()), primary, dto.getSecretKey()
                , new Money(new BigDecimal(250), dto.getCurrency()), new Money(new BigDecimal(12), dto.getCurrency()));
    }
}
