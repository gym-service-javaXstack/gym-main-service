package com.example.springcore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserCredentialsDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
