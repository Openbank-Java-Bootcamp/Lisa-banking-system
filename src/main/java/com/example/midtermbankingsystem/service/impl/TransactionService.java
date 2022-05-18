package com.example.midtermbankingsystem.service.impl;


import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Money;
import com.example.midtermbankingsystem.model.Transaction;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.TransactionRepository;
import com.example.midtermbankingsystem.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    public List<Transaction> getAllTransactions() {
        return null;
    }

    public Transaction getTransactionById(Integer id) {
        return null;
    }

    public Transaction createTransaction(TransactionDTO dto) {

        //todo first validate transaction


        var payerAccount = accountRepository.findById(dto.getPayerAccId());

        var targetAccount = accountRepository.findById(dto.getTargetAccId());

        var transaction = Transaction.fromDTO(dto, payerAccount, targetAccount);

        //todo apply transaction
        applyTransaction(transaction.getAmount().getAmount(), payerAccount, targetAccount);

        try {
            //todo if payer is null create only with target, if target is null create only with payer, if both null error
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Transaction");
        }


    }

    public void applyTransaction(BigDecimal amount, Optional<Account> payer, Optional<Account> target) {

        //if payer is in our db deduct the amount
        if(payer.isPresent()) {
            var payerBalance = payer.get().getBalance().getAmount();
            payer.get().setBalance(new Money(payerBalance.subtract(amount), payer.get().getBalance().getCurrency()));
            accountRepository.save(payer.get());
        }

        //if target is in our db add the amount
        if(target.isPresent()) {
            var targetBalance = target.get().getBalance().getAmount();
            target.get().setBalance(new Money(targetBalance.add(amount), target.get().getBalance().getCurrency()));
            accountRepository.save(target.get());
        }

        if(payer.isEmpty()&& target.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                , "Payer or Target Account must be in our banking system");

    }


    public void updateTransaction(Integer id, Transaction transaction) {

    }

    public void deleteTransaction(Integer id) {

    }
}
