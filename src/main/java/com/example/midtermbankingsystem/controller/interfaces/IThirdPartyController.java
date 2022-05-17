package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.example.midtermbankingsystem.model.ThirdParty;

public interface IThirdPartyController {
    ThirdParty saveThirdParty(ThirdPartyDTO thirdPartyDTO);
}
