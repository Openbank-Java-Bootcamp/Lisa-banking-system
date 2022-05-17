package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.IThirdPartyController;
import com.example.midtermbankingsystem.model.ThirdParty;
import com.example.midtermbankingsystem.service.interfaces.IThirdPartyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/third_parties")
public class ThirdPartyController implements IThirdPartyController {

    private IThirdPartyService thirdPartyService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty saveThirdParty(ThirdParty thirdParty) {
        return thirdPartyService.saveThirdParty(thirdParty);
    }
}
