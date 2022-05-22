package com.ironhack.midtermbankingsystem.controller.impl;

import com.ironhack.midtermbankingsystem.DTO.AdminDTO;
import com.ironhack.midtermbankingsystem.controller.interfaces.IAdminController;
import com.ironhack.midtermbankingsystem.model.Admin;
import com.ironhack.midtermbankingsystem.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin saveAdmin(@RequestBody @Valid AdminDTO adminDTO) {
        return adminService.saveAdmin(adminDTO);
    }

}
