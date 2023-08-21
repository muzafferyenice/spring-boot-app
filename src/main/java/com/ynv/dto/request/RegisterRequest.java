package com.ynv.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	
    @Size(max = 50)
    @NotNull(message = "Please provide your first name")
    private String firstName;
    
    @Size(max = 50)
    @NotNull(message = "Please provide your last name")
    private String lastName;

    @Size(max = 30)
    @NotNull(message = "Please provide your user name")
    private String username;

    @Size(max = 100)
    @NotNull(message = "Please provide your address")
    private String address;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please provide valid phone number")
    @Size(min = 14, max = 14)
    @NotNull(message = "Please provide your phone number")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide your birthDate")
    private LocalDate birthDate;


    @Email(message = "Please provide valid email")
    @Size(min = 5, max = 20)
    @NotNull(message = "Please provide your email")
    private String email;

    @Size(min = 4, max = 20,message="Please Provide Correct Size for Password")
    @NotNull(message = "Please provide your password")
    private String password;

    @Size(min = 4, max = 20,message="Please enter location")
    @NotNull(message = "Please provide your location")
    private String location ;



}