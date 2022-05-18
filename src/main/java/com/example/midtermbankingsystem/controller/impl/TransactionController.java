package com.example.midtermbankingsystem.controller.impl;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.controller.interfaces.ITransactionController;
import com.example.midtermbankingsystem.model.Transaction;
import com.example.midtermbankingsystem.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController implements ITransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction saveTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }
}
