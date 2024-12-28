package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.response.ReviewResponse;
import com.noisy.rrssProject.model.dto.request.ReviewRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Review;
import com.noisy.rrssProject.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> responseList = reviewService.getAllReviews();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest review) {
        Review createdReview = reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody ReviewRequest review) {
        ReviewResponse updatedReview = reviewService.updateReview(id, review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}
