package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.repository.CheckingAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CheckingAccountService implements ICheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    public List<CheckingAccount> getAllCheckingAccounts() {
        List<CheckingAccount> checkingAccountList = checkingAccountRepository.findAll();
        if (checkingAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Accounts found in the database");
        }
        return checkingAccountList;
    }

    public CheckingAccount getCheckingAccountById(String id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Account found with that ID");
        } else {
            return foundCheckingAccount.get();
        }
    }

    public CheckingAccount saveCheckingAccount(CheckingAccount checkingAccount) {
        return null;
    }

    public void updateCheckingAccount(String id, CheckingAccount checkingAccount) {

    }

    public void deleteCheckingAccount(String id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        checkingAccountRepository.delete(foundCheckingAccount.get());
    }
}
