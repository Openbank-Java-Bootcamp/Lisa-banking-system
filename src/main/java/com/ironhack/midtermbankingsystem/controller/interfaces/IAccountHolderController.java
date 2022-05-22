package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.AccountHolderDTO;
import com.ironhack.midtermbankingsystem.model.AccountHolder;

public interface IAccountHolderController {
    AccountHolder saveAccountHolder(AccountHolderDTO accountHolderDTO);
    AccountHolder deleteAccountHolder(Integer id);
}
