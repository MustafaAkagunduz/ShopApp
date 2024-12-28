package com.noisy.rrssProject.model.dto.response;

import com.noisy.rrssProject.model.enums.RewardType;

public record RewardResponse(
        Long id,
        Long couponId,
        String code,
        RewardType rewardType,
        int points
) {}
