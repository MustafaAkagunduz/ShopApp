package com.noisy.rrssProject.model.dto.response;

import com.noisy.rrssProject.model.enums.Gender;

import java.util.List;

public record ModeratorResponse(
        Long id,
        String name,
        String username,
        String email,
        Gender gender,
        List<ModeratorCommunityResponse> community,
        String profile_picture
) {}