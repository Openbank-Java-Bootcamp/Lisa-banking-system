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

    public Admin(Integer id, String name, String password) {
        super(id, name, password, Role.ADMIN);
    }
}
