package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.model.SavingsAccount;

import java.util.List;

public interface ISavingsAccountService {
    List<SavingsAccount> getAllSavingsAccounts();
    SavingsAccount getSavingsAccountById(String id);
    SavingsAccount saveSavingsAccount(SavingsAccount savingsAccount);
    void updateSavingsAccount(String id, SavingsAccount savingsAccount);
    void deleteSavingsAccount(String id);
}
