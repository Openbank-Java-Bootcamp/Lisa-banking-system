package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.CreditCardAccount;
import com.example.midtermbankingsystem.repository.CreditCardAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICreditCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardAccountService implements ICreditCardAccountService {

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    public List<CreditCardAccount> getAllCreditCardAccounts() {
        List<CreditCardAccount> creditCardAccountList = creditCardAccountRepository.findAll();
        if (creditCardAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Accounts found in the database");
        }
        return creditCardAccountList;
    }

    public CreditCardAccount getCreditCardAccountById(String id) {
        Optional<CreditCardAccount> foundCreditCardAccount = creditCardAccountRepository.findById(id);
        if (foundCreditCardAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        } else {
            return foundCreditCardAccount.get();
        }
    }

    public CreditCardAccount saveCreditCardAccount(CreditCardAccount creditCardAccount) {
        return null;
    }

    public void updateCreditCardAccount(String id, CreditCardAccount creditCardAccount) {

    }

    public void deleteCreditCardAccount(String id) {
        Optional<CreditCardAccount> foundCreditCardAccount = creditCardAccountRepository.findById(id);
        if (foundCreditCardAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        creditCardAccountRepository.delete(foundCreditCardAccount.get());
    }
}
