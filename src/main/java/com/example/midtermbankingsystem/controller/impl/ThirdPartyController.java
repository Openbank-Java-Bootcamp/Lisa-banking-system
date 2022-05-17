package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.example.midtermbankingsystem.controller.interfaces.IThirdPartyController;
import com.example.midtermbankingsystem.model.ThirdParty;
import com.example.midtermbankingsystem.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/third-parties")
public class ThirdPartyController implements IThirdPartyController {

    @Autowired
    private IThirdPartyService thirdPartyService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty saveThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyService.saveThirdParty(thirdPartyDTO);
    }
}
