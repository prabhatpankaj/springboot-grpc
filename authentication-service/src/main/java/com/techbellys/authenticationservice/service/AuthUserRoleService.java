package com.techbellys.authenticationservice.service;

import com.techbellys.authenticationservice.model.AuthUser;
import com.techbellys.authenticationservice.model.AuthUserRole;
import com.techbellys.authenticationservice.repository.AuthUserRepository;
import com.techbellys.authenticationservice.repository.AuthUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthUserRoleService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthUserRoleRepository roleRepository;

    public boolean assignRoleToUser(String username, String roleName) {
        AuthUser user = authUserRepository.findByUsername(username);
        AuthUserRole role = roleRepository.findByName(roleName);

        if (user != null && role != null) {
            Set<AuthUserRole> roles = user.getAuthUserRoles();
            if (roles == null) {
                roles = new HashSet<>();
            }
            roles.add(role);
            user.setAuthUserRoles(roles);
            authUserRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean createRole(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            AuthUserRole role = new AuthUserRole();
            role.setName(roleName);
            roleRepository.save(role);
            return true;
        }
        return false;
    }

    public boolean removeRoleFromUser(String username, String roleName) {
        AuthUser user = authUserRepository.findByUsername(username);
        AuthUserRole role = roleRepository.findByName(roleName);

        if (user != null && role != null) {
            Set<AuthUserRole> roles = user.getAuthUserRoles();
            roles.remove(role);
            user.setAuthUserRoles(roles);
            authUserRepository.save(user);
            return true;
        }
        return false;
    }
}
