package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AccountHolder> getAllAccountHolders() {
        List<AccountHolder> accountHolderList = accountHolderRepository.findAll();
        if (accountHolderList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found in the database");
        }
        return accountHolderList;
    }

    public AccountHolder getAccountHolderById(Integer id) {
        Optional<AccountHolder> foundAccountHolder = accountHolderRepository.findById(id);
        if (foundAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with that ID");
        } else {
            return foundAccountHolder.get();
        }
    }

    public AccountHolder saveAccountHolder(AccountHolder accountHolder) {
        return null;
    }

    public void updateAccountHolder(Integer id, AccountHolder accountHolder) {

    }

    public void deleteAccountHolder(Integer id) {
        Optional<AccountHolder> foundAccountHolder = accountHolderRepository.findById(id);
        if (foundAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with that ID");
        }
        accountHolderRepository.delete(foundAccountHolder.get());
    }
}
