package com.noisy.rrssProject.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerUpdate(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password,
        String phoneNumber,
        String profilePicture
) {
}
