package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.model.Transaction;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public interface ITransactionController {
    Transaction saveTransaction(TransactionDTO transactionDTO);
    Transaction saveThirdPartyTransaction(HttpHeaders headers, TransactionDTO transactionDTO);
}
