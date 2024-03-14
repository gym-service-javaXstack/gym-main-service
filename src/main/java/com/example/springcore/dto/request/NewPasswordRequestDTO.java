package com.example.springcore.dto.request;

import com.example.springcore.dto.UserCredentialsDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewPasswordRequestDTO extends UserCredentialsDTO {

    @NotEmpty
    private String newPassword;

}
