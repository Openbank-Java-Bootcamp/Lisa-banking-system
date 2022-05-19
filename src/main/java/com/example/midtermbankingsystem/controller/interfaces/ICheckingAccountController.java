package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.Account;

public interface ICheckingAccountController {
    Account saveCheckingAccount(CheckingAccountDTO checkingAccountDTO);

}
