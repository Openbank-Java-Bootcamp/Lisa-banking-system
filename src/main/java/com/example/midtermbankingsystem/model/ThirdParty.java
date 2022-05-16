package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ThirdParty extends User{
    private String hashedKey;
    private final Role role = Role.THIRD_PARTY;
}
