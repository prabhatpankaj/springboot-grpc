package com.techbellys.authenticationservice.repository;

import com.techbellys.authenticationservice.model.AuthUserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthUserAddressRepository extends JpaRepository<AuthUserAddress, Long> {
    List<AuthUserAddress> findByUserId(Long userId);
}