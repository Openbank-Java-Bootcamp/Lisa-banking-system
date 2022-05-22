package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.ironhack.midtermbankingsystem.model.ThirdParty;
import com.ironhack.midtermbankingsystem.repository.ThirdPartyRepository;
import com.ironhack.midtermbankingsystem.service.interfaces.IThirdPartyService;
import com.ironhack.midtermbankingsystem.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThirdPartyService implements IThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Utils utils;


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

    public ThirdParty saveThirdParty(ThirdPartyDTO thirdPartyDTO) {

        utils.validateUsernameIsUnique(thirdPartyDTO.getUsername());

        ThirdParty thirdParty = ThirdParty.fromDTO(thirdPartyDTO);
        thirdParty.setPassword(passwordEncoder.encode(thirdPartyDTO.getPassword()));
        utils.validateKeyIsUnique(thirdPartyDTO.getHashedKey());
        utils.validateThirdPartyIsUnique(thirdPartyDTO.getThirdPartyAccount());
        thirdParty.setHashedKey(thirdPartyDTO.getHashedKey());

        try {
            return thirdPartyRepository.save(thirdParty);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Third Party doesn't exist.");
        }
    }

    public void deleteThirdParty(Integer id) {
        Optional<ThirdParty> foundThirdParty = thirdPartyRepository.findById(id);
        if (foundThirdParty.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Third Party found with that ID");
        }
        thirdPartyRepository.delete(foundThirdParty.get());
    }
}
