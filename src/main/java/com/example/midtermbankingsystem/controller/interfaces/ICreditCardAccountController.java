package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.model.CreditCardAccount;

public interface ICreditCardAccountController {
    CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO creditCardAccountDTO);
    CreditCardAccount getCreditCardAccountById(Integer id);
}
