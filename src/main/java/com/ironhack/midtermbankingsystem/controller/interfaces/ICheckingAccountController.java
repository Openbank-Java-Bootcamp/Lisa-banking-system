package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.ironhack.midtermbankingsystem.model.Account;

public interface ICheckingAccountController {
    Account saveCheckingAccount(CheckingAccountDTO checkingAccountDTO);

}
