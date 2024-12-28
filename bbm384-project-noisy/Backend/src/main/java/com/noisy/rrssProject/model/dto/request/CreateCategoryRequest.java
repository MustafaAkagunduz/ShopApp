package com.noisy.rrssProject.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @NotNull
        String name,
        @NotNull
        String parentName
) {}
