package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.controller.interfaces.ICheckingAccountController;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/checking-accounts")
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    private ICheckingAccountService checkingAccountService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccount saveCheckingAccount(@RequestBody @Valid CheckingAccountDTO checkingAccountDTO) {
        return checkingAccountService.saveCheckingAccount(checkingAccountDTO);
    }
}
