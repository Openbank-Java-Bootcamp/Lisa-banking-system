package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.Admin;
import com.example.midtermbankingsystem.repository.AdminRepository;
import com.example.midtermbankingsystem.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        List<Admin> adminList = adminRepository.findAll();
        if(adminList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Admin found in the database");
        }
        return adminList;
    }

    public Admin getAdminById(Integer id) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if(foundAdmin.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Admin found with that ID");
        } else {
            return foundAdmin.get();
        }
    }

    public Admin saveAdmin(Admin admin) {
        return null;
    }

    public void updateAdmin(Integer id, Admin admin) {

    }

    public void deleteAdmin(Integer id) {

    }
}
