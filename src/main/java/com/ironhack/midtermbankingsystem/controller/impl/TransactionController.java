package com.ironhack.midtermbankingsystem.controller.impl;


import com.ironhack.midtermbankingsystem.DTO.TransactionDTO;
import com.ironhack.midtermbankingsystem.controller.interfaces.ITransactionController;
import com.ironhack.midtermbankingsystem.model.Transaction;
import com.ironhack.midtermbankingsystem.repository.ThirdPartyRepository;
import com.ironhack.midtermbankingsystem.service.interfaces.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
public class TransactionController implements ITransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;


    @PostMapping("/account-holder/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction saveTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @PostMapping("/third-party/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction saveThirdPartyTransaction(@RequestHeader HttpHeaders headers, @RequestBody @Valid TransactionDTO transactionDTO) {

        String header = Objects.requireNonNull(headers.get("hashedKey")).toString();
        String key = header.substring(1, header.length()-1);
        log.info("hashed key is {} ", key);

        var foundThirdParty = Optional.ofNullable(thirdPartyRepository.findByHashedKey(key))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Hashed Key" + key));

        if(!foundThirdParty.getThirdPartyAccount().equals(transactionDTO.getThirdPartyAccount())) throw new
                ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Third Party Account");

        return transactionService.createThirdPartyTransaction(transactionDTO);
    }

}