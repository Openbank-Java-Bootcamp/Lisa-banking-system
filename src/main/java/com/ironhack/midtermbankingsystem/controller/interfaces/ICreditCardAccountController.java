package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.ironhack.midtermbankingsystem.model.CreditCardAccount;

public interface ICreditCardAccountController {
    CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO creditCardAccountDTO);
    CreditCardAccount getCreditCardAccountById(Integer id);
}
