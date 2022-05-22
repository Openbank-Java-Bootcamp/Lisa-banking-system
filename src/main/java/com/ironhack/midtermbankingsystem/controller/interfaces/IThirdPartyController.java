package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.ironhack.midtermbankingsystem.model.ThirdParty;

public interface IThirdPartyController {
    ThirdParty saveThirdParty(ThirdPartyDTO thirdPartyDTO);
}
