package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.AccountHolderDTO;
import com.example.midtermbankingsystem.model.AccountHolder;

import java.util.List;

public interface IAccountHolderService {
    List<AccountHolder> getAllAccountHolders();
    AccountHolder getAccountHolderById(Integer id);
    AccountHolder saveAccountHolder(AccountHolderDTO accountHolderDTO);
    void updateAccountHolder(Integer id, AccountHolder accountHolder);
    AccountHolder deleteAccountHolder(Integer id);
}
