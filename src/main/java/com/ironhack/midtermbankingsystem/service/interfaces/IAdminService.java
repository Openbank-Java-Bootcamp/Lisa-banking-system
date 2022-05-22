package com.ironhack.midtermbankingsystem.service.interfaces;

import com.ironhack.midtermbankingsystem.DTO.AdminDTO;
import com.ironhack.midtermbankingsystem.model.Admin;

import java.util.List;

public interface IAdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);
    Admin saveAdmin(AdminDTO adminDTO);
    void updateAdmin(Integer id, Admin admin);
    void deleteAdmin(Integer id);
}
