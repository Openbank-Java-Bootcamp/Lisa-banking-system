package com.example.midtermbankingsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyDTO {
    private String name;
    private String password;
    private String hashedKey;
}
