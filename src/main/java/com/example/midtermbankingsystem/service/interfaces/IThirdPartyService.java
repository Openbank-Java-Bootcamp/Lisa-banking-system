package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.example.midtermbankingsystem.model.ThirdParty;

import java.util.List;

public interface IThirdPartyService {
    List<ThirdParty> getAllThirdParties();
    ThirdParty getThirdPartyById(Integer id);
    ThirdParty saveThirdParty(ThirdPartyDTO thirdPartyDTO);
    void updateThirdParty(Integer id, ThirdParty thirdParty);
    void deleteThirdParty(Integer id);
}
