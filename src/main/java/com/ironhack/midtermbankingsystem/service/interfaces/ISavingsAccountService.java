package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.ironhack.midtermbankingsystem.model.SavingsAccount;

import java.util.List;

public interface ISavingsAccountService {
    List<SavingsAccount> getAllSavingsAccounts();
    SavingsAccount getSavingsAccountById(Integer id);
    SavingsAccount saveSavingsAccount(SavingsAccountDTO savingsAccountDTO);
    void addInterest(SavingsAccount savingsAccount);
    void updateSavingsAccount(Integer id, SavingsAccount savingsAccount);
    void deleteSavingsAccount(Integer id);
}
