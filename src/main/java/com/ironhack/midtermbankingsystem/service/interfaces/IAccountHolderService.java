package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.AccountHolderDTO;
import com.ironhack.midtermbankingsystem.model.AccountHolder;

import java.util.List;

public interface IAccountHolderService {
    List<AccountHolder> getAllAccountHolders();
    AccountHolder getAccountHolderById(Integer id);
    AccountHolder saveAccountHolder(AccountHolderDTO accountHolderDTO);
    void updateAccountHolder(Integer id, AccountHolder accountHolder);
    AccountHolder deleteAccountHolder(Integer id);
}
