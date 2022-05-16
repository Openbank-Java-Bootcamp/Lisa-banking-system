package com.example.midtermbankingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CheckingAccount extends Account{
    private String secretKey;

    private Money minimumBalance;

    private Money monthlyMaintenanceFee;

}
