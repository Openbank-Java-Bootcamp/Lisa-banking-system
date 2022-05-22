package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.AccountStatusDTO;
import com.example.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.example.midtermbankingsystem.controller.interfaces.IAccountController;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;
import com.example.midtermbankingsystem.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;


    @PatchMapping("/admin/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account patchAccountStatus(@PathVariable("id")Integer id, @RequestBody @Valid AccountStatusDTO accountStatusDTO) {
        return accountService.changeAccountStatus(id,accountStatusDTO);
    }

    @PatchMapping("/admin/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account patchAccountBalance(@PathVariable("id")Integer id, @RequestBody @Valid AccountBalanceDTO accountBalanceDTO) {
        return accountService.changeAccountBalance(id, accountBalanceDTO);
    }

    @GetMapping("/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getAccountBalance(@PathVariable("id")Integer id){
        return accountService.getAccountBalance(id);
    }

    @DeleteMapping("/admin/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account deleteAccount(@PathVariable("id")Integer id){
        return accountService.deleteAccount(id);
    }
}
