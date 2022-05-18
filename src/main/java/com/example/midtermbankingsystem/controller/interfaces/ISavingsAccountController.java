package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.example.midtermbankingsystem.model.SavingsAccount;

public interface ISavingsAccountController {
    SavingsAccount saveSavingsAccount(SavingsAccountDTO savingsAccountDTO);

}
