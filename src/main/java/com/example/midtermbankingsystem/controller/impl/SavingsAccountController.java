package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.ISavingsAccountController;
import com.example.midtermbankingsystem.model.SavingsAccount;
import com.example.midtermbankingsystem.service.interfaces.ISavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/savings-accounts")
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    private ISavingsAccountService savingsAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount saveSavingsAccount(SavingsAccount savingsAccount) {
        return savingsAccountService.saveSavingsAccount(savingsAccount);
    }
}
