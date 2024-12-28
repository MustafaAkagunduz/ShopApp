package com.noisy.rrssProject.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ReviewRequest(
        @NotNull
        Long customerID,
        @NotNull
        Long productID,
        @NotNull
        //max min rating could be added
        int rating,
        @Size(max = 400, message = "Comment length must shorter than 400 characters")        
        String comment,
        @NotNull
        LocalDateTime reviewDate

) {}
