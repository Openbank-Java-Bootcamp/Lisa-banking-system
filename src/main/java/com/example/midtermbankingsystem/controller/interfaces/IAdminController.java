package com.example.midtermbankingsystem.controller.interfaces;

import com.example.midtermbankingsystem.DTO.AdminDTO;
import com.example.midtermbankingsystem.model.Admin;

public interface IAdminController {
    Admin saveAdmin(AdminDTO adminDTO);
}
