package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.model.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountService {
    List<CreditCardAccount> getAllCreditCardAccounts();
    CreditCardAccount getCreditCardAccountById(String id);
    CreditCardAccount saveCreditCardAccount(CreditCardAccount creditCardAccount);
    void updateCreditCardAccount(String id, CreditCardAccount creditCardAccount);
    void deleteCreditCardAccount(String id);
}
