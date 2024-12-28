package com.noisy.rrssProject.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotNull
        @Size(min = 3, max = 50, message = "name length must be between 3 and 50 characters")
        String name,
        @Size(max = 400, message = "Content length must shorter than 400 characters")
        String description,
        @NotNull
        double price,
        @NotNull
        String categoryName,
        @NotNull
        Long merchantId,
        @NotNull
        int stock,
        String image
) {}
