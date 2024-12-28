package com.noisy.rrssProject.model.dto.response;

import java.util.List;

public record CommunityResponse(
        String name,
        String description,
        List<CommunityModeratorResponse> moderators
) {}
