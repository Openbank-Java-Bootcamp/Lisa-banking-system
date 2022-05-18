package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.model.CreditCardAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.CreditCardAccountRepository;
import com.example.midtermbankingsystem.service.interfaces.ICreditCardAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardAccountService implements ICreditCardAccountService {

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    public List<CreditCardAccount> getAllCreditCardAccounts() {
        List<CreditCardAccount> creditCardAccountList = creditCardAccountRepository.findAll();
        if (creditCardAccountList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Accounts found in the database");
        }
        return creditCardAccountList;
    }

    public CreditCardAccount getCreditCardAccountById(String id) {
        Optional<CreditCardAccount> foundCreditCardAccount = creditCardAccountRepository.findById(id);
        if (foundCreditCardAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        } else {
            return foundCreditCardAccount.get();
        }
    }

    public CreditCardAccount saveCreditCardAccount(CreditCardAccountDTO dto) {
        var primaryOwner = accountHolderRepository.findById(dto.getPrimaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getPrimaryOwner()));


        var secondaryOwner = dto.getSecondaryOwner() != null
                ? accountHolderRepository.findById(dto.getSecondaryOwner())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Account Holder found with ID " + dto.getSecondaryOwner()))
                : null;


        var creditCardAccount = secondaryOwner != null
                ? CreditCardAccount.fromDTO(dto, primaryOwner, secondaryOwner)
                : CreditCardAccount.fromDTO(dto, primaryOwner);

        try {return creditCardAccountRepository.save(creditCardAccount);}
        catch(Exception e) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Credit Card Account");}
    }

    public void updateCreditCardAccount(String id, CreditCardAccount creditCardAccount) {

    }

    public void deleteCreditCardAccount(String id) {
        Optional<CreditCardAccount> foundCreditCardAccount = creditCardAccountRepository.findById(id);
        if (foundCreditCardAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Credit Card Account found with that ID");
        }
        creditCardAccountRepository.delete(foundCreditCardAccount.get());
    }
}
