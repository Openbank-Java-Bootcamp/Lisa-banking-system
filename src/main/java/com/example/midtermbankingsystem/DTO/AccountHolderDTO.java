package com.example.midtermbankingsystem.DTO;

import com.example.midtermbankingsystem.enums.Role;
import com.example.midtermbankingsystem.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderDTO {
    private String name;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Address primaryAddress;
    private Address mailingAddress;
    private Role role;
}
