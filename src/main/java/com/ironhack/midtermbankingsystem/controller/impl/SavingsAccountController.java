package com.ironhack.midtermbankingsystem.controller.impl;

import com.ironhack.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.ironhack.midtermbankingsystem.controller.interfaces.ISavingsAccountController;
import com.ironhack.midtermbankingsystem.model.SavingsAccount;
import com.ironhack.midtermbankingsystem.service.interfaces.ISavingsAccountService;
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
    public SavingsAccount saveSavingsAccount(@RequestBody @Valid SavingsAccountDTO savingsAccountDTO) {
        return savingsAccountService.saveSavingsAccount(savingsAccountDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount getSavingsAccountById(@PathVariable Integer id) {
        return savingsAccountService.getSavingsAccountById(id);
    }
}
