package com.noisy.rrssProject.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @NotNull
        Long id,
        @Size(max = 400, message = "Content length must shorter than 400 characters")        
        String content
) {}
//Bu tarz recordlar da oluşturmalıyız. Update methodları çok saçma bi vaziyette şuan...
