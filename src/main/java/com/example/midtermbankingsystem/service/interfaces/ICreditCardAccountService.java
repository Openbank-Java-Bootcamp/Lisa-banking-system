package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.model.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountService {
    List<CreditCardAccount> getAllCreditCardAccounts();
    CreditCardAccount getCreditCardAccountById(Integer id);
    CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO creditCardAccountDTO);
    void addInterest(CreditCardAccount creditCardAccount);
    void updateCreditCardAccount(Integer id, CreditCardAccount creditCardAccount);
    void deleteCreditCardAccount(Integer id);
}
