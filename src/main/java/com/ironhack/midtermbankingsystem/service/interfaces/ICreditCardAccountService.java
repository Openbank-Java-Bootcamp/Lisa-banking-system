package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.ironhack.midtermbankingsystem.model.CreditCardAccount;

import java.util.List;

public interface ICreditCardAccountService {
    List<CreditCardAccount> getAllCreditCardAccounts();
    CreditCardAccount getCreditCardAccountById(Integer id);
    CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO creditCardAccountDTO);
    void addInterest(CreditCardAccount creditCardAccount);
    void updateCreditCardAccount(Integer id, CreditCardAccount creditCardAccount);
    void deleteCreditCardAccount(Integer id);
}
