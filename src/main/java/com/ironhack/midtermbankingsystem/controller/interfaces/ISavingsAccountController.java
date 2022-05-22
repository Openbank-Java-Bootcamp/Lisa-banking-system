package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.ironhack.midtermbankingsystem.model.SavingsAccount;

public interface ISavingsAccountController {
    SavingsAccount saveSavingsAccount(SavingsAccountDTO savingsAccountDTO);
    SavingsAccount getSavingsAccountById(Integer id);
}
