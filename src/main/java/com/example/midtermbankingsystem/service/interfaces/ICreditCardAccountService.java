package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.model.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountService {
    List<CreditCardAccount> getAllCreditCardAccounts();
    CreditCardAccount getCreditCardAccountById(String id);
    CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO creditCardAccountDTO);
    void updateCreditCardAccount(String id, CreditCardAccount creditCardAccount);
    void deleteCreditCardAccount(String id);
}
