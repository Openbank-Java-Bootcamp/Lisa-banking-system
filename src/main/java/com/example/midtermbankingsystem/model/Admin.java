package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.AdminDTO;
import com.example.midtermbankingsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@Entity
public class Admin extends User{
    //private final Role role = Role.ADMIN;

    public Admin( String name, String password) {
        super( name, password, Role.ADMIN);
    }

    public static Admin fromDTO(AdminDTO dto) {
        return new Admin(dto.getName(), dto.getPassword());
    }
}
