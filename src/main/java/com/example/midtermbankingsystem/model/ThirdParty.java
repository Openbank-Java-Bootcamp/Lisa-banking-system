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
    private Integer thirdPartyAccount;

    public ThirdParty( String name, String password, String username, String hashedKey, Integer thirdPartyAccount) {
        super(name, password, Role.THIRD_PARTY, username);
        this.hashedKey = hashedKey;
        this.thirdPartyAccount = thirdPartyAccount;
    }


    public static ThirdParty fromDTO(ThirdPartyDTO dto) {
        return new ThirdParty(dto.getName(), dto.getPassword(), dto.getUsername(), dto.getHashedKey(), dto.getThirdPartyAccount());
    }

}
