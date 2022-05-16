package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.model.ThirdParty;
import com.example.midtermbankingsystem.repository.ThirdPartyRepository;
import com.example.midtermbankingsystem.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ThirdPartyService implements IThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ThirdParty> getAllThirdParties() {
        List<ThirdParty> thirdPartyList = thirdPartyRepository.findAll();
        if (thirdPartyList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Third Parties found in the database");
        }
        return thirdPartyList;
    }

    public ThirdParty getThirdPartyById(Integer id) {
        Optional<ThirdParty> foundThirdParty = thirdPartyRepository.findById(id);
        if (foundThirdParty.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Third Party found with that ID");
        } else {
            return foundThirdParty.get();
        }
    }

    public ThirdParty saveThirdParty(ThirdParty thirdParty) {
        try {
            return thirdPartyRepository.save(new ThirdParty(
                    thirdParty.getName(),
                    passwordEncoder.encode(thirdParty.getPassword()),
                    thirdParty.getHashedKey()
            ));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed Third Party");
        }
    }

    public void updateThirdParty(Integer id, ThirdParty thirdParty) {
        Optional<ThirdParty> foundThirdParty = thirdPartyRepository.findById(id);
        if (foundThirdParty.isPresent()) {
            if (thirdParty.getName() != null) {
                foundThirdParty.get().setName(thirdParty.getName());
            }
            if (thirdParty.getPassword() != null) {
                foundThirdParty.get().setPassword(passwordEncoder.encode(thirdParty.getPassword()));
            }
            if (thirdParty.getHashedKey() != null) {
                foundThirdParty.get().setHashedKey(thirdParty.getHashedKey());
            }

            thirdPartyRepository.save(foundThirdParty.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Blog Post doesn't exist.");
        }
    }

    public void deleteThirdParty(Integer id) {
        Optional<ThirdParty> foundThirdParty = thirdPartyRepository.findById(id);
        if (foundThirdParty.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Blog Post found with that ID");
        }
        thirdPartyRepository.delete(foundThirdParty.get());
    }
}
