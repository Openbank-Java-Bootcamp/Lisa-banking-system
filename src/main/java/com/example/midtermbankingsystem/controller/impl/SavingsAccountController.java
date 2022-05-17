package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.ISavingsAccountController;
import com.example.midtermbankingsystem.model.SavingsAccount;
import com.example.midtermbankingsystem.service.interfaces.ISavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/savings-accounts")
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    private ISavingsAccountService savingsAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount saveSavingsAccount(@RequestBody @Valid SavingsAccount savingsAccount) {
        return savingsAccountService.saveSavingsAccount(savingsAccount);
    }
}
