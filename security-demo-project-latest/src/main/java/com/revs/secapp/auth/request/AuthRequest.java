package com.revs.secapp.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @NotBlank(message = "VALIDATION.AUTH.EMAIL.NOT_BLANK")
    @Email(message = "VALIDATION.AUTH.EMAIL.FORMAT")
    @Schema(example = "email@email.com")
    private String email;

    @NotBlank(message = "VALIDATION.AUTH.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 20, message = "VALIDATION.AUTH.PASSWORD.SIZE")
    @Schema(example = "password")
    private String password;

}
