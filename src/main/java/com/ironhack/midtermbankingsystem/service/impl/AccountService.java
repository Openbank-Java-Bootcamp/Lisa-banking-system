package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.ironhack.midtermbankingsystem.DTO.AccountStatusDTO;
import com.ironhack.midtermbankingsystem.model.Account;
import com.ironhack.midtermbankingsystem.model.Money;
import com.ironhack.midtermbankingsystem.repository.AccountRepository;
import com.ironhack.midtermbankingsystem.service.interfaces.IAccountService;
import com.ironhack.midtermbankingsystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Utils utils;


    public Account getAccountById(Integer id) {
        Optional<Account> foundAccount = accountRepository.findById(id);
        if (foundAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account found with that ID");
        } else {
            return foundAccount.get();
        }
    }

    public Account changeAccountStatus(Integer id, AccountStatusDTO accountStatusDTO) {
        Account account = getAccountById(id);

        try {
            account.setStatus(accountStatusDTO.getStatus());
            accountRepository.save(account);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Status value not valid.");
        }

        return account;
    }

    public Account changeAccountBalance(Integer id, AccountBalanceDTO accountBalanceDTO) {
        Account account = getAccountById(id);

        try {
            account.setBalance(new Money(accountBalanceDTO.getBalance(), account.getBalance().getCurrency()));
            accountRepository.save(account);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status value not valid.");
        }
        return account;
    }

    public Money getAccountBalance(Integer id) {
        Account account = getAccountById(id);

        utils.validateLoggedUserIsAccOwner(account);

        return account.getBalance();
    }

    public Account deleteAccount(Integer id) {
        Account foundAccount = getAccountById(id);

        accountRepository.delete(foundAccount);

        return foundAccount;
    }
}
