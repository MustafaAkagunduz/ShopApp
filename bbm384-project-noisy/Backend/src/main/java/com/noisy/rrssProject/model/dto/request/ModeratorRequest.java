package com.noisy.rrssProject.model.dto.request;

import com.noisy.rrssProject.model.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record ModeratorRequest(
        @NotNull
        String name,
        @NotBlank(message = "Username is required")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only letters and numbers")
        String username,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @Size(min = 6, max = 25, message = "Password length must be between 6 and 25 characters")
        String password,
        Gender gender,
        @Size(min = 11, max = 11, message = "05554443322 Phone number's length must be equal to 11.")
        @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numbers")
        String phoneNumber,
        String profilePicture
) {}
