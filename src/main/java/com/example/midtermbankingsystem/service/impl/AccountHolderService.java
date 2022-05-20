package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.AccountHolderDTO;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.service.interfaces.IAccountHolderService;
import com.example.midtermbankingsystem.utils.Utils;
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

    @Autowired
    private Utils utils;


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

    public AccountHolder saveAccountHolder(AccountHolderDTO accountHolderDTO) {

        utils.validateUsernameIsUnique(accountHolderDTO.getUsername());

        AccountHolder accountHolder = AccountHolder.fromDTO(accountHolderDTO);
        accountHolder.setPassword(passwordEncoder.encode(accountHolderDTO.getPassword()));

        try {
            return accountHolderRepository.save(accountHolder);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Account Holder");
        }
    }

    public void updateAccountHolder(Integer id, AccountHolder accountHolder) {
        Optional<AccountHolder> foundAccountHolder = accountHolderRepository.findById(id);
        if (foundAccountHolder.isPresent()) {
            if (accountHolder.getName() != null) {
                foundAccountHolder.get().setName(accountHolder.getName());
            }
            if (accountHolder.getPassword() != null) {
                foundAccountHolder.get().setPassword(passwordEncoder.encode(accountHolder.getPassword()));
            }
            if (accountHolder.getDateOfBirth() != null) {
                foundAccountHolder.get().setDateOfBirth(accountHolder.getDateOfBirth());
            }
            if (accountHolder.getPrimaryAddress() != null) {
                foundAccountHolder.get().setPrimaryAddress(accountHolder.getPrimaryAddress());
            }
            if (accountHolder.getMailingAddress() != null) {
                foundAccountHolder.get().setMailingAddress(accountHolder.getMailingAddress());
            }
            accountHolderRepository.save(foundAccountHolder.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Account Holder doesn't exist.");
        }
    }

    public void deleteAccountHolder(Integer id) {
        Optional<AccountHolder> foundAccountHolder = accountHolderRepository.findById(id);
        if (foundAccountHolder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with that ID");
        }
        accountHolderRepository.delete(foundAccountHolder.get());
    }
}
