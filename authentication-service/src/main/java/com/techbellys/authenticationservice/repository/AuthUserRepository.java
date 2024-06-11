package com.techbellys.authenticationservice.repository;

import com.techbellys.authenticationservice.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    public AuthUser findByUsername(String username);

    public AuthUser findByEmail(String email);
}
