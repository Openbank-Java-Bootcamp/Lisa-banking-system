package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckingAccountDTO {

    private Money balance;
    private Integer primaryOwner;
    private Integer secondaryOwner;
    private String secretKey;
    private Money minimumBalance;
    private Money monthlyMaintenanceFee;
}
