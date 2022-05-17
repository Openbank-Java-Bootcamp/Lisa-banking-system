package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.AccountHolderDTO;
import com.example.midtermbankingsystem.model.AccountHolder;

public interface IAccountHolderController {
    AccountHolder saveAccountHolder(AccountHolderDTO accountHolderDTO);
}
