package com.example.midtermbankingsystem.model;

import com.example.midtermbankingsystem.DTO.ThirdPartyDTO;
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

    public ThirdParty( String name, String password, String username, String hashedKey) {
        super(name, password, Role.THIRD_PARTY, username);
        this.hashedKey = hashedKey;
    }


    public static ThirdParty fromDTO(ThirdPartyDTO dto) {
        return new ThirdParty(dto.getName(), dto.getPassword(), dto.getUsername(), dto.getHashedKey());
    }

}
