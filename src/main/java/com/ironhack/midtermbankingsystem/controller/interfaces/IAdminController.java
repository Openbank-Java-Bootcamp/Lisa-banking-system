package com.ironhack.midtermbankingsystem.controller.interfaces;

import com.ironhack.midtermbankingsystem.DTO.AdminDTO;
import com.ironhack.midtermbankingsystem.model.Admin;

public interface IAdminController {
    Admin saveAdmin(AdminDTO adminDTO);
}
