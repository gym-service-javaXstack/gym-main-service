package com.example.springcore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String jwt;

    public AuthenticationResponseDTO(String jwt) {
        this.jwt = jwt;
    }
}
