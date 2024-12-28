package com.noisy.rrssProject.model.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        String title,

        @Size(max = 400, message = "Content length must shorter than 400 characters")        
        String content,
        @NotNull
        LocalDateTime postDate,
        @NotNull
        Long customerId,
        @NotNull
        String community
) {}

