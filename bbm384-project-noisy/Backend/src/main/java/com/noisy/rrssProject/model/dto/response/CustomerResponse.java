package com.noisy.rrssProject.model.dto.response;

import com.noisy.rrssProject.model.enums.Gender;
import com.noisy.rrssProject.model.enums.UserType;

import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String username,
        String email,
        Gender gender,
        String profilePicture,
        List<PostResponse> posts,
        List<RewardResponse> rewards,
        UserType role
) {}
