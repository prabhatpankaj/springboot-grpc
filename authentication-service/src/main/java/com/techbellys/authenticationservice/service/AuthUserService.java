package com.techbellys.authenticationservice.service;

import com.techbellys.authenticationservice.model.AuthUser;
import com.techbellys.authenticationservice.model.AuthUserAddress;
import com.techbellys.authenticationservice.model.AuthUserRole;
import com.techbellys.authenticationservice.repository.AuthUserAddressRepository;
import com.techbellys.authenticationservice.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private AuthUserRepository authUserRepository;

    private AuthUserAddressRepository authUserAddressRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username);

        if (authUser != null) {
            return User.withUsername(authUser.getUsername())
                    .password(authUser.getPassword())
                    .roles(authUser.getAuthUserRoles().stream().map(AuthUserRole::getName).toArray(String[]::new))
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }

    public AuthUser saveUser(AuthUser user) {
        return authUserRepository.save(user);
    }
    public List<AuthUserAddress> getUserAddresses(Long userId) {
        return authUserAddressRepository.findByUserId(userId);
    }

    public void addAddressToUser(Long userId, AuthUserAddress address) {
        Optional<AuthUser> userOpt = authUserRepository.findById(Math.toIntExact(userId));
        if (userOpt.isPresent()) {
            address.setUser(userOpt.get());
            authUserAddressRepository.save(address);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteAddress(Long addressId) {
        authUserAddressRepository.deleteById(addressId);
    }
}
