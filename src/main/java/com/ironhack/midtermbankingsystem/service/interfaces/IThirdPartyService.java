package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.ironhack.midtermbankingsystem.model.ThirdParty;

import java.util.List;

public interface IThirdPartyService {
    List<ThirdParty> getAllThirdParties();
    ThirdParty getThirdPartyById(Integer id);
    ThirdParty saveThirdParty(ThirdPartyDTO thirdPartyDTO);
    void updateThirdParty(Integer id, ThirdParty thirdParty);
    void deleteThirdParty(Integer id);
}
