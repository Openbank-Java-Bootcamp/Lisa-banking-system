package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.AccountStatusDTO;
import com.ironhack.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.ironhack.midtermbankingsystem.model.Account;
import com.ironhack.midtermbankingsystem.model.Money;

public interface IAccountController {
    Account patchAccountStatus(Integer id, AccountStatusDTO accountStatusDTO);
    Account patchAccountBalance(Integer id, AccountBalanceDTO accountBalanceDTO);
    Money getAccountBalance(Integer id);
    Account deleteAccount(Integer id);
}
