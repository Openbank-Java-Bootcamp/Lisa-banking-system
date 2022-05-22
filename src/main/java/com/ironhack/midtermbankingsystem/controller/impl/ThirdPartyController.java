package com.ironhack.midtermbankingsystem.controller.impl;

import com.ironhack.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.ironhack.midtermbankingsystem.controller.interfaces.IThirdPartyController;
import com.ironhack.midtermbankingsystem.model.ThirdParty;
import com.ironhack.midtermbankingsystem.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/third-parties")
public class ThirdPartyController implements IThirdPartyController {

    @Autowired
    private IThirdPartyService thirdPartyService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty saveThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyService.saveThirdParty(thirdPartyDTO);
    }
}
