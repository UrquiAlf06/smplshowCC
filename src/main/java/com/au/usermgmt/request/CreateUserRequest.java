package com.au.usermgmt.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {
    private long Id;

    @NotBlank(message = "First Name mandatory")
    private String firstName;

    @NotBlank(message = "Last Name mandatory")
    private String lastName;

    @NotBlank(message = "Email mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password mandatory")
    private String password;

    @NotNull(message = "Role is mandatory")
    private Long roleId;
}
