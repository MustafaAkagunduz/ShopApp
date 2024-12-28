package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.ModeratorNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.ModeratorDTOMapper;
import com.noisy.rrssProject.model.dto.request.ModeratorRequest;
import com.noisy.rrssProject.model.dto.response.ModeratorResponse;
import com.noisy.rrssProject.model.enums.AccountType;
import com.noisy.rrssProject.repository.ConfirmationTokenRepository;
import com.noisy.rrssProject.repository.ModeratorRepository;
import com.noisy.rrssProject.model.entity.Community;
import com.noisy.rrssProject.model.entity.ConfirmationToken;
import com.noisy.rrssProject.model.entity.Moderator;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModeratorService {
    private final ModeratorRepository moderatorRepository;
    private final ModeratorDTOMapper mapper;
    private final CommunityService communityService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public ModeratorService(ModeratorRepository moderatorRepository, ModeratorDTOMapper mapper, CommunityService communityService, ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService) {
        this.moderatorRepository = moderatorRepository;
        this.mapper = mapper;
        this.communityService = communityService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    public ModeratorResponse getModeratorById(Long id){
        return moderatorRepository.findById(id).map(mapper).orElseThrow(()->
                new ModeratorNotFoundException("Moderator could not find by id: "+id));
    }
    public Moderator findModeratorById(Long id){
        return moderatorRepository.findById(id).orElseThrow(()->
                new ModeratorNotFoundException("Moderator could not find by id: "+id));
    }

    public List<ModeratorResponse> getAllModerators() {
        return moderatorRepository.
                findAll().
                stream().
                map(mapper).
                collect(Collectors.toList());
    }

    public Moderator createModerator(ModeratorRequest moderator) {
        return moderatorRepository.save(mapper.moderatorRequestToModerator(moderator));
    }



    public ModeratorResponse updateModerator(Long id, Moderator moderator) {
        moderatorRepository.deleteById(id);
        moderator.setId(id);
        Moderator updatedModerator = moderatorRepository.save(moderator);
        return mapper.apply(updatedModerator);
    }

    public void deleteModerator(Long id) {
        moderatorRepository.deleteById(id);
    }

    public ModeratorResponse assignCommunityToModerator(Long moderatorId, String communityName) {
        List<Community> communities;
        Moderator moderator = findModeratorById(moderatorId);
        Community community = communityService.findCommunityByName(communityName);
        communities = moderator.getCommunities();
        communities.add(community);
        moderator.setCommunities(communities);
        moderatorRepository.save(moderator);
        return mapper.apply(moderator);
    }

    public void dissociateCommunityFromModerator(Long moderatorId, String communityName) {
        Moderator moderator = findModeratorById(moderatorId);
        Community community = communityService.findCommunityByName(communityName);

        if (moderator != null && community != null) {
            moderator.getCommunities().remove(community);
            moderatorRepository.save(moderator);
        }
    }

    public ResponseEntity<?> saveModerator(ModeratorRequest request) {

        Moderator moderator = mapper.moderatorRequestToModerator(request);

        if (moderatorRepository.existsByEmail(moderator.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        moderatorRepository.save(moderator);
        emailService.sendEmail(moderator);


        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            Moderator moderator = moderatorRepository.findByEmailIgnoreCase(token.getModerator().getEmail());
            moderator.setEnabled(true);
            moderator.setAccountType(AccountType.MODERATOR_ACTIVE);
            moderatorRepository.save(moderator);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

}
