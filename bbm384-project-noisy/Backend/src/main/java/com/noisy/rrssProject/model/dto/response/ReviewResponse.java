package com.noisy.rrssProject.model.dto.response;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String customerUsername,
        String productName,
        Long productId,
        int rating,
        String comment,
        LocalDateTime reviewDate
) {}
