package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.CommunityRequest;
import com.noisy.rrssProject.model.dto.response.CommunityResponse;
import com.noisy.rrssProject.model.entity.Community;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.service.CommunityService;

@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<CommunityResponse> getCommunityByName(@PathVariable String name) {
        CommunityResponse response = communityService.getCommunityByName(name);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommunityResponse>> getAllCommunities() {
        List<CommunityResponse> responseList = communityService.getAllCommunities();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all/{name}")
    public ResponseEntity<List<CommunityResponse>> getAllCommunities(@PathVariable String name) {
        List<CommunityResponse> responseList = communityService.getAllCommunitiesByModeratorName(name);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Community> createCommunity(@RequestBody CommunityRequest community) {
        Community createdCommunity = communityService.createCommunity(community);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommunity);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CommunityResponse> updateCommunity(@PathVariable String name, @RequestBody CommunityRequest community) {
        CommunityResponse updatedCommunity = communityService.updateCommunity(name, community);
        return ResponseEntity.ok(updatedCommunity);

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCommunity(@PathVariable String name) {
        communityService.deleteCommunity(name);
        return ResponseEntity.ok("Community deleted successfully.");
    }
}
