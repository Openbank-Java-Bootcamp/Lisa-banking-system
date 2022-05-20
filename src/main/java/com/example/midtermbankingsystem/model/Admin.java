package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.AdminDTO;
import com.example.midtermbankingsystem.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@Entity
public class Admin extends User {

    public Admin(String name, String password, String username) {super(name, password, Role.ADMIN, username);}

    public static Admin fromDTO(AdminDTO dto) {return new Admin(dto.getName(),  dto.getPassword(), dto.getUsername());}
}
