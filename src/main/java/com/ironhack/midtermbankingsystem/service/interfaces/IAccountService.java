package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.ironhack.midtermbankingsystem.DTO.AccountStatusDTO;
import com.ironhack.midtermbankingsystem.model.Account;
import com.ironhack.midtermbankingsystem.model.Money;

public interface IAccountService {
    Account getAccountById(Integer id);
    Account changeAccountStatus(Integer id, AccountStatusDTO accountStatusDTO);
    Account changeAccountBalance(Integer id, AccountBalanceDTO accountBalanceDTO);
    Money getAccountBalance(Integer id);
    Account deleteAccount(Integer id);
}
