package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.model.AccountHolder;

import java.util.List;

public interface IAccountHolderService {
    List<AccountHolder> getAllAccountHolders();
    AccountHolder getAccountHolderById(Integer id);
    AccountHolder saveAccountHolder(AccountHolder accountHolder);
    void updateAccountHolder(Integer id, AccountHolder accountHolder);
    void deleteAccountHolder(Integer id);
}
