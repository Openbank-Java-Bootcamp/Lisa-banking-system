package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@Entity
public class Admin extends User{
    //private final Role role = Role.ADMIN;

    public Admin( String name, String password) {
        super( name, password, Role.ADMIN);
    }
}
