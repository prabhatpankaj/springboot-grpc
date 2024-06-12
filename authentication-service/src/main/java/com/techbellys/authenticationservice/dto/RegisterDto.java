package com.techbellys.authenticationservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterDto {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phone;

    private String address;

    @NotEmpty
    @Size(min = 6, message = "Minimum password length is 6 charactors")
    private String password;
}