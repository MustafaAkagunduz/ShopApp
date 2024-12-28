package com.noisy.rrssProject.model.dto.request;

import com.noisy.rrssProject.model.enums.RewardType;
import jakarta.validation.constraints.NotNull;

public record RewardRequest(
        @NotNull
        Long couponId,
        @NotNull
        RewardType rewardType,
        @NotNull
        int points
) {}
