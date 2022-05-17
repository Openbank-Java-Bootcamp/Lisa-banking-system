package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.ICreditCardAccountController;
import com.example.midtermbankingsystem.model.CreditCardAccount;
import com.example.midtermbankingsystem.service.interfaces.ICreditCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credit-card-accounts")
public class CreditCardAccountController implements ICreditCardAccountController {

    @Autowired
    private ICreditCardAccountService creditCardAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccount saveCreditCardAccount(@RequestBody @Valid CreditCardAccount creditCardAccount) {
        return creditCardAccountService.saveCreditCardAccount(creditCardAccount);
    }
}
