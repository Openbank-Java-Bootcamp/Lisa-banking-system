package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.model.CheckingAccount;

import java.util.List;

public interface ICheckingAccountService {
    List<CheckingAccount> getAllCheckingAccounts();
    CheckingAccount getCheckingAccountById(String id);
    CheckingAccount saveCheckingAccount(CheckingAccount checkingAccount);
    void updateCheckingAccount(String id, CheckingAccount checkingAccount);
    void deleteCheckingAccount(String id);
}
