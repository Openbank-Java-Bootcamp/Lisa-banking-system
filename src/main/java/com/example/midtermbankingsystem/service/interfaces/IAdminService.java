package com.example.midtermbankingsystem.service.interfaces;

import com.example.midtermbankingsystem.model.Admin;

import java.util.List;

public interface IAdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);
    Admin saveAdmin(Admin admin);
    void updateAdmin(Integer id, Admin admin);
    void deleteAdmin(Integer id);
}
