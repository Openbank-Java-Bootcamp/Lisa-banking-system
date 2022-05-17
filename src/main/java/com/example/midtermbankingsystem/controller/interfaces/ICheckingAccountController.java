package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.CheckingAccount;

public interface ICheckingAccountController {
    CheckingAccount saveCheckingAccount(CheckingAccountDTO checkingAccountDTO);

}
