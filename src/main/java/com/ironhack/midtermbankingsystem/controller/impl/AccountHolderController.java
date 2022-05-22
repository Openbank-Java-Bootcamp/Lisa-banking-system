package com.ironhack.midtermbankingsystem.controller.impl;

import com.ironhack.midtermbankingsystem.DTO.AccountHolderDTO;
import com.ironhack.midtermbankingsystem.controller.interfaces.IAccountHolderController;
import com.ironhack.midtermbankingsystem.model.AccountHolder;
import com.ironhack.midtermbankingsystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account-holders")
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService accountHolderService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder saveAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return accountHolderService.saveAccountHolder(accountHolderDTO);
    }

    @DeleteMapping("/admin/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder deleteAccountHolder(@PathVariable("id")Integer id){
        return accountHolderService.deleteAccountHolder(id);
    }
}
