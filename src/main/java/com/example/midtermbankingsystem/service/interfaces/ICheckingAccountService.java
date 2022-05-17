package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.CheckingAccount;

import java.util.List;

public interface ICheckingAccountService {
    List<CheckingAccount> getAllCheckingAccounts();
    CheckingAccount getCheckingAccountById(String id);
    CheckingAccount saveCheckingAccount(CheckingAccountDTO checkingAccountDTO);
    void updateCheckingAccount(String id, CheckingAccount checkingAccount);
    void deleteCheckingAccount(String id);
}
