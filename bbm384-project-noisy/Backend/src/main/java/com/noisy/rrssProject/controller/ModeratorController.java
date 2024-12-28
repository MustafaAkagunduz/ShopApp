package com.noisy.rrssProject.controller;

import java.util.List;
import java.util.Optional;

import com.noisy.rrssProject.model.dto.request.ModeratorRequest;
import com.noisy.rrssProject.model.dto.response.ModeratorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Moderator;
import com.noisy.rrssProject.service.ModeratorService;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {
    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeratorResponse> getModeratorById(@PathVariable Long id) {
        ModeratorResponse response = moderatorService.getModeratorById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ModeratorResponse>> getAllModerators() {
        List<ModeratorResponse> responseList = moderatorService.getAllModerators();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Moderator> createModerator(@RequestBody ModeratorRequest moderator) {
        Moderator createdModerator = moderatorService.createModerator(moderator);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModerator);
    }

    @PutMapping("/assign")
    public ResponseEntity<ModeratorResponse> assignCommunityToModerator(
            @RequestParam("moderatorId") Long moderatorId, @RequestParam("communityName") String communityName) {
        ModeratorResponse assignedModerator = moderatorService.assignCommunityToModerator(moderatorId,communityName);
        return ResponseEntity.ok(assignedModerator);
    }
    @PutMapping("/dissociate")
    public ResponseEntity<String> dissociateCommunityFromModerator(
            @RequestParam("moderatorId") Long moderatorId, @RequestParam("communityName") String communityName) {
        moderatorService.dissociateCommunityFromModerator(moderatorId,communityName);
        return ResponseEntity.ok("Community removed from the moderator succesfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeratorResponse> updateModerator(@PathVariable Long id, @RequestBody Moderator moderator) {
        ModeratorResponse updatedModerator = moderatorService.updateModerator(id, moderator);
        return ResponseEntity.ok(updatedModerator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModerator(@PathVariable Long id) {
        moderatorService.deleteModerator(id);
        return ResponseEntity.ok("Moderator deleted successfully.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody ModeratorRequest moderator) {
        return moderatorService.saveModerator(moderator);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return moderatorService.confirmEmail(confirmationToken);
    }

}
