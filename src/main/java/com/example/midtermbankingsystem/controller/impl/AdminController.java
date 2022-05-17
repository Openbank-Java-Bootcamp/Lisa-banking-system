package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.controller.interfaces.IAdminController;
import com.example.midtermbankingsystem.model.Admin;
import com.example.midtermbankingsystem.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin saveAdmin(Admin admin) {
        return adminService.saveAdmin(admin);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello from Admin";
    }
}
