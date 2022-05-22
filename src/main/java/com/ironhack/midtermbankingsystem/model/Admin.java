package com.ironhack.midtermbankingsystem.model;

import com.ironhack.midtermbankingsystem.DTO.AdminDTO;
import com.ironhack.midtermbankingsystem.enums.Role;
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
