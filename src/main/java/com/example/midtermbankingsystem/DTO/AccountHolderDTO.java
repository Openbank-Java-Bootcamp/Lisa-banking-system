package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.enums.Role;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.Address;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountHolderDTO {
    private String name;
    private String password;
    private LocalDate dateOfBirth;
    private Address primaryAddress;
    private Address mailingAddress;
    private Role role;


}
