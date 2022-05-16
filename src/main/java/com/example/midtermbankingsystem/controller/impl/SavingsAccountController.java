package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.ISavingsAccountController;
import com.example.midtermbankingsystem.service.interfaces.ISavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    private ISavingsAccountService savingsAccountService;
}
