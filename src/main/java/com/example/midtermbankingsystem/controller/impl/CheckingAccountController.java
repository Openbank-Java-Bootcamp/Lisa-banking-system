package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.ICheckingAccountController;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checking-accounts")
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    private ICheckingAccountService checkingAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccount saveCheckingAccount(CheckingAccount checkingAccount) {
        return checkingAccountService.saveCheckingAccount(checkingAccount);
    }
}
