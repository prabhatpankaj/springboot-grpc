package com.techbellys.authenticationservice.controller;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.techbellys.authenticationservice.dto.LoginDto;
import com.techbellys.authenticationservice.dto.RegisterDto;
import com.techbellys.authenticationservice.dto.UserResponseDto;
import com.techbellys.authenticationservice.model.AuthUser;
import com.techbellys.authenticationservice.model.AuthUserAddress;
import com.techbellys.authenticationservice.model.AuthUserRole;
import com.techbellys.authenticationservice.repository.AuthUserRepository;
import com.techbellys.authenticationservice.repository.AuthUserRoleRepository;
import com.techbellys.authenticationservice.service.AuthUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/account")
public class AuthController {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthUserRoleRepository authUserRoleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(Authentication auth) {
        System.out.println(auth.getDetails()+ "--------------------------");
        var response = new HashMap<String, Object>();
        response.put("Username", auth.getName());
        response.put("Authorities", auth.getAuthorities());
        var authUser = authUserRepository.findByUsername(auth.getName());
        UserResponseDto userResponseDto = getUserResponseDto(authUser);
        response.put("user", userResponseDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterDto registerDto, BindingResult result) {
        if (result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (ObjectError objectError : errorList) {
                var error = (FieldError) objectError;
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }
        AuthUser authUser = getAuthUser(registerDto);

        try {
            var otherUser = authUserRepository.findByUsername(registerDto.getUsername());
            if (otherUser != null) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
            otherUser = authUserRepository.findByEmail(registerDto.getEmail());
            if (otherUser != null) {
                return ResponseEntity.badRequest().body("Email already in use");
            }

            authUserRepository.save(authUser);

            AuthUser registerUser = authUserRepository.findByUsername(registerDto.getUsername());

            String jwtToken = createJwtToken(authUser);

            UserResponseDto userResponseDto = getUserResponseDto(registerUser);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", userResponseDto);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            System.out.println("There is an Exception while register: " + ex.getMessage());
        }

        return ResponseEntity.badRequest().body("Error");
    }

    private AuthUser getAuthUser(RegisterDto registerDto) {
        var bCryptEncoder = new BCryptPasswordEncoder();

        AuthUser authUser = new AuthUser();
        authUser.setFirstName(registerDto.getFirstName());
        authUser.setLastName(registerDto.getLastName());
        authUser.setUsername(registerDto.getUsername());
        authUser.setEmail(registerDto.getEmail());
        authUser.setPhone(registerDto.getPhone());
        authUser.setAddress(registerDto.getAddress());
        authUser.setCreatedAt(new Date());
        authUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

        Set<AuthUserRole> roles = new HashSet<>();
        AuthUserRole defaultRole = authUserRoleRepository.findByName("USER");
        if (defaultRole != null) {
            roles.add(defaultRole);
        } else {
            AuthUserRole newRole = new AuthUserRole();
            newRole.setName("USER");
            authUserRoleRepository.save(newRole);
            roles.add(newRole);
        }
        authUser.setAuthUserRoles(roles);

        return authUser;
    }

    private UserResponseDto getUserResponseDto(AuthUser registerUser) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(registerUser.getId());
        userResponseDto.setFirstName(registerUser.getFirstName());
        userResponseDto.setLastName(registerUser.getLastName());
        userResponseDto.setUsername(registerUser.getUsername());
        userResponseDto.setEmail(registerUser.getEmail());
        userResponseDto.setRoles(registerUser.getAuthUserRoles().stream().map(AuthUserRole::getName).toArray(String[]::new));
        userResponseDto.setCreatedAt(registerUser.getCreatedAt());
        return userResponseDto;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {

        if (result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (ObjectError objectError : errorList) {
                var error = (FieldError) objectError;
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            AuthUser appUser = authUserRepository.findByUsername(loginDto.getUsername());

            UserResponseDto userResponseDto = getUserResponseDto(appUser);
            String jwtToken = createJwtToken(appUser);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", userResponseDto);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            System.out.println("There is an Exception while login: " + ex.getMessage());
        }

        return ResponseEntity.badRequest().body("Wrong Username or Password");
    }

    private String createJwtToken(AuthUser authUser) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(authUser.getUsername())
                .claim("roles", authUser.getAuthUserRoles().stream().map(AuthUserRole::getName).toArray(String[]::new))
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return encoder.encode(params).getTokenValue();
    }

    @PostMapping("/add-address")
    public ResponseEntity<Object> addAddress(Authentication auth, @Valid @RequestBody AuthUserAddress address) {
        var authUser = authUserRepository.findByUsername(auth.getName());
        if (authUser == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        address.setUser(authUser);
        authUserService.addAddressToUser(authUser.getId(), address);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/delete-address/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable Long id) {
        try {
            authUserService.deleteAddress(id);
            return ResponseEntity.ok("Address deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error deleting address");
        }
    }
}
