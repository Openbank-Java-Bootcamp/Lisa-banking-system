package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.CheckingAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICheckingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CheckingAccountService implements ICheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public List<CheckingAccount> getAllCheckingAccounts() {
        List<CheckingAccount> checkingAccountList = checkingAccountRepository.findAll();
        if (checkingAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Accounts found in the database");
        }
        return checkingAccountList;
    }

    public CheckingAccount getCheckingAccountById(Integer id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Checking Account found with that ID");
        } else {
            return foundCheckingAccount.get();
        }
    }

    public CheckingAccount createCheckingAccount(CheckingAccountDTO dto) {

        var primaryOwner = accountHolderRepository.findById(dto.getPrimaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getPrimaryOwner()));


        var secondaryOwner = dto.getSecondaryOwner() != null
                ? accountHolderRepository.findById(dto.getSecondaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getSecondaryOwner()))
                : null;


        var checkingAccount = secondaryOwner != null
                ? CheckingAccount.fromDTO(dto, primaryOwner, secondaryOwner)
                : CheckingAccount.fromDTO(dto, primaryOwner);

        try {return checkingAccountRepository.save(checkingAccount);}
        catch(Exception e) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Checking Account");}
    }


    public void updateCheckingAccount(Integer id, CheckingAccount checkingAccount) {

    }

    public void deleteCheckingAccount(Integer id) {
        Optional<CheckingAccount> foundCheckingAccount = checkingAccountRepository.findById(id);
        if (foundCheckingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        checkingAccountRepository.delete(foundCheckingAccount.get());
    }
}
