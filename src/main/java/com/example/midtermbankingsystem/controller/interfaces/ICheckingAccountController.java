package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.CheckingAccount;

public interface ICheckingAccountController {
    Account saveCheckingAccount(CheckingAccountDTO checkingAccountDTO);

}
