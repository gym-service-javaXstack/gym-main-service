package com.example.springcore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NewPasswordRequestDTO extends UserCredentialsDTO {

    @NotEmpty
    private String newPassword;

}
