package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.AdminDTO;
import com.ironhack.midtermbankingsystem.model.Admin;
import com.ironhack.midtermbankingsystem.repository.AdminRepository;
import com.ironhack.midtermbankingsystem.service.interfaces.IAdminService;
import com.ironhack.midtermbankingsystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Utils utils;

    public List<Admin> getAllAdmins() {
        List<Admin> adminList = adminRepository.findAll();
        if (adminList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Admin found in the database");
        }
        return adminList;
    }

    public Admin getAdminById(Integer id) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Admin found with that ID");
        } else {
            return foundAdmin.get();
        }
    }

    public Admin saveAdmin(AdminDTO adminDTO) {

        utils.validateUsernameIsUnique(adminDTO.getUsername());

        Admin admin = Admin.fromDTO(adminDTO);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        try {
            return adminRepository.save(admin);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Admin");
        }
    }

    public void updateAdmin(Integer id, Admin admin) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isPresent()) {
            if (admin.getName() != null) {
                foundAdmin.get().setName(admin.getName());
            }
            if (admin.getPassword() != null) {
                foundAdmin.get().setPassword(passwordEncoder.encode(admin.getPassword()));
            }
            adminRepository.save(foundAdmin.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Admin doesn't exist.");
        }
    }

    public void deleteAdmin(Integer id) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Admin found with that ID");
        }
        adminRepository.delete(foundAdmin.get());
    }
}
