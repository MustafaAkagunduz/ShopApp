package com.noisy.rrssProject.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank(message = "UserId is required")
        Long userId,
        @NotBlank(message = "PostId is required")
        Long postId,
        @Size(max = 400, message = "Content length must shorter than 400 characters")
        //check the max size
        String content
) {}
