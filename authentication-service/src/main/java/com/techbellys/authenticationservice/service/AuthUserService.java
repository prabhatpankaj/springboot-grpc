package com.techbellys.authenticationservice.service;

import com.techbellys.authenticationservice.model.AuthUser;
import com.techbellys.authenticationservice.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {
    @Autowired
    private AuthUserRepository repo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = repo.findByUsername(username);

        if(authUser != null) {
            return User.withUsername(authUser.getUsername())
                    .password(authUser.getPassword())
                    .roles(authUser.getRole())
                    .build();
        }
        return null;
    }
}
