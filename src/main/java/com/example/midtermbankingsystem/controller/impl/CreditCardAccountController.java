package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.ICreditCardAccountController;
import com.example.midtermbankingsystem.service.interfaces.ICreditCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CreditCardAccountController implements ICreditCardAccountController {

    @Autowired
    private ICreditCardAccountService creditCardAccountService;
}
