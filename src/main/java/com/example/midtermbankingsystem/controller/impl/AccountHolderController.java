package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.IAccountHolderController;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService accountHolderService;

    @PostMapping()
    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {
        return null;
    }
}
