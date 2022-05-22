package com.example.midtermbankingsystem.utils;

import com.example.midtermbankingsystem.enums.Role;
import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.ThirdParty;
import com.example.midtermbankingsystem.model.User;
import com.example.midtermbankingsystem.repository.ThirdPartyRepository;
import com.example.midtermbankingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class Utils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;


    public void validateLoggedUserIsAccOwner(Account account) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Role role = userRepository.findByUsername(username).getRole();

        if(role!=Role.ADMIN) {
            if (account.getSecondaryOwner() == null ? !account.getPrimaryOwner().getUsername().equals(username)
                    : !account.getPrimaryOwner().getUsername().equals(username) && !account.getSecondaryOwner().getUsername().equals(username)) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to access this account as logged user isn't the owner");
            }
        }
    }

    public void validateUsernameIsUnique(String username){
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create User, username already exists");
        }
    }

    public void validateKeyIsUnique(String key){
        Optional<ThirdParty> thirdParty = Optional.ofNullable(thirdPartyRepository.findByHashedKey(key));

        if(thirdParty.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create Third Party, hashed key already exists");

        }
    }
}
