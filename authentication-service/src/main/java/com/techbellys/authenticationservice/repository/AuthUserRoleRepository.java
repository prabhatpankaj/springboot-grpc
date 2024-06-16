package com.techbellys.authenticationservice.repository;

import com.techbellys.authenticationservice.model.AuthUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRoleRepository extends JpaRepository<AuthUserRole, Long> {
    AuthUserRole findByName(String name);
}