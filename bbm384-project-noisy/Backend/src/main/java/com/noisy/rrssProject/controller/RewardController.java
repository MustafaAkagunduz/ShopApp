package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.RewardRequest;
import com.noisy.rrssProject.model.dto.response.RewardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Reward;
import com.noisy.rrssProject.service.RewardService;

@RestController
@RequestMapping("/reward")
public class RewardController {
    private final RewardService rewardService;
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardResponse> getRewardById(@PathVariable Long id) {
        RewardResponse response = rewardService.getRewardById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RewardResponse>> getAllRewards() {
        List<RewardResponse> responseList = rewardService.getAllRewards();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Reward> createReward(@RequestBody RewardRequest reward) {
        Reward createdReward = rewardService.createReward(reward);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReward);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RewardResponse> updateReward(@PathVariable Long id, @RequestBody RewardRequest reward) {
        RewardResponse updatedReward = rewardService.updateReward(id, reward);
        return ResponseEntity.ok(updatedReward);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReward(@PathVariable Long id) {
        rewardService.deleteReward(id);
        return ResponseEntity.ok("Reward deleted successfully.");
    }

}
