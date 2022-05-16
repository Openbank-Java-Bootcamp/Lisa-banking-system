package com.example.midtermbankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

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

}
