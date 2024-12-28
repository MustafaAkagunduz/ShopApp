package com.noisy.rrssProject.model.dto.response;

import java.util.List;

public record CustomerProductListResponse (
        Long id,
        String name,
        String username,
        List<ProductResponse> products,
        double price
) {}
