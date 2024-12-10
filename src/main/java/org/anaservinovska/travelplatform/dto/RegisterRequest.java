package org.anaservinovska.travelplatform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String[] roles;  // For simplicity, an array of roles
}
