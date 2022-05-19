package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.example.midtermbankingsystem.DTO.AccountStatusDTO;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;

public interface IAccountService {
    Account getAccountById(Integer id);
    Account changeAccountStatus(Integer id, AccountStatusDTO accountStatusDTO);
    Account changeAccountBalance(Integer id, AccountBalanceDTO accountBalanceDTO);
    Money getAccountBalance(Integer id);
}
