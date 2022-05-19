package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.example.midtermbankingsystem.model.SavingsAccount;

import java.util.List;

public interface ISavingsAccountService {
    List<SavingsAccount> getAllSavingsAccounts();
    SavingsAccount getSavingsAccountById(Integer id);
    SavingsAccount saveSavingsAccount(SavingsAccountDTO savingsAccountDTO);
    void addInterest(SavingsAccount savingsAccount);
    void updateSavingsAccount(Integer id, SavingsAccount savingsAccount);
    void deleteSavingsAccount(Integer id);
}
