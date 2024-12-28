package com.noisy.rrssProject.model.dto.response;

import com.noisy.rrssProject.model.enums.AccountType;
import com.noisy.rrssProject.model.entity.Product;

import java.util.List;

public record MerchantResponse (
    Long id,
    String name,
    String email,
    AccountType active,
    List<Product> products
) {}
