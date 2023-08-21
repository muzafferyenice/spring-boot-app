package com.ynv.dto.request;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message="Please provide a user name")
    private String username;

    @NotNull(message="Please provide a password")
    private String password;
}