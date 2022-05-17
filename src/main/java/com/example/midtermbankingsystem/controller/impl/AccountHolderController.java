package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.IAccountHolderController;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account_holders")
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService accountHolderService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder saveAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
        return accountHolderService.saveAccountHolder(accountHolder);
    }
}
