package com.example.midtermbankingsystem.controller.interfaces;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.model.Transaction;

public interface ITransactionController {
    Transaction saveTransaction(TransactionDTO transactionDTO);
}
