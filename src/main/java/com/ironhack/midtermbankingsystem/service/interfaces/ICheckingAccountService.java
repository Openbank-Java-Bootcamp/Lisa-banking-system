package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.ironhack.midtermbankingsystem.model.CheckingAccount;

import java.util.List;

public interface ICheckingAccountService {
    List<CheckingAccount> getAllCheckingAccounts();
    CheckingAccount getCheckingAccountById(Integer id);
    CheckingAccount createCheckingAccount(CheckingAccountDTO checkingAccountDTO);
    void updateCheckingAccount(Integer id, CheckingAccount checkingAccount);
    void deleteCheckingAccount(Integer id);
}
