package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.AccountStatusDTO;
import com.example.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;

public interface IAccountController {
    Account patchAccountStatus(Integer id, AccountStatusDTO accountStatusDTO);
    Account patchAccountBalance(Integer id, AccountBalanceDTO accountBalanceDTO);
    Money getAccountBalance(Integer id);
}
