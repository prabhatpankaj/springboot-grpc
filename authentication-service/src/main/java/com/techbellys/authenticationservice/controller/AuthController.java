package com.techbellys.authenticationservice.controller;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.techbellys.authenticationservice.dto.LoginDto;
import com.techbellys.authenticationservice.dto.RegisterDto;
import com.techbellys.authenticationservice.model.AuthUser;
import com.techbellys.authenticationservice.repository.AuthUserRepository;
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

@RestController
@RequestMapping("/account")
public class AuthController {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(Authentication auth) {
        var response = new HashMap<String, Object>();
        response.put("Username", auth.getName());
        response.put("Authorities", auth.getAuthorities());

        var authUser = authUserRepository.findByUsername(auth.getName());
        response.put("User", authUser);
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

        var bCryptEncoder = new BCryptPasswordEncoder();

        AuthUser authUser = new AuthUser();
        authUser.setFirstName(registerDto.getFirstName());
        authUser.setLastName(registerDto.getLastName());
        authUser.setUsername(registerDto.getUsername());
        authUser.setEmail(registerDto.getEmail());
        authUser.setRole("client");
        authUser.setCreatedAt(new Date());
        authUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

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

            String jwtToken = createJwtToken(authUser);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", authUser);

            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            System.out.println("There is an Exception while register");
        }

        return ResponseEntity.badRequest().body("Error");
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
            String jwtToken = createJwtToken(appUser);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", appUser);

            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            System.out.println("There is an Exception while register");
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
                .claim("role",authUser.getRole())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return encoder.encode(params).getTokenValue();
    }

}