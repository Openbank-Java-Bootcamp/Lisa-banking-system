package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.SavingsAccount;
import com.example.midtermbankingsystem.repository.SavingsAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ISavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsAccountService implements ISavingsAccountService {

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    public List<SavingsAccount> getAllSavingsAccounts() {
        List<SavingsAccount> savingsAccountList = savingsAccountRepository.findAll();
        if (savingsAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Accounts found in the database");
        }
        return savingsAccountList;
    }

    public SavingsAccount getSavingsAccountById(String id) {
        Optional<SavingsAccount> foundSavingsAccount = savingsAccountRepository.findById(id);
        if (foundSavingsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account found with that ID");
        } else {
            return foundSavingsAccount.get();
        }
    }

    public SavingsAccount saveSavingsAccount(SavingsAccount savingsAccount) {
        return null;
    }

    public void updateSavingsAccount(String id, SavingsAccount savingsAccount) {

    }

    public void deleteSavingsAccount(String id) {
        Optional<SavingsAccount> foundSavingsAccount = savingsAccountRepository.findById(id);
        if (foundSavingsAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account found with that ID");
        }
        savingsAccountRepository.delete(foundSavingsAccount.get());
    }
}
