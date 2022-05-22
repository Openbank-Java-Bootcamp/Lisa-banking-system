package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.AccountHolderDTO;
import com.example.midtermbankingsystem.model.AccountHolder;
import org.springframework.web.bind.annotation.PathVariable;

public interface IAccountHolderController {
    AccountHolder saveAccountHolder(AccountHolderDTO accountHolderDTO);
    AccountHolder deleteAccountHolder(Integer id);
}
