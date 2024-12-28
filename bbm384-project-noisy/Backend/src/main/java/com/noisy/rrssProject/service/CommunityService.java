package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.CommunityNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.CommunityDTOMapper;
import com.noisy.rrssProject.model.dto.request.CommunityRequest;
import com.noisy.rrssProject.model.dto.response.CommunityResponse;
import com.noisy.rrssProject.repository.CommunityRepository;
import com.noisy.rrssProject.model.entity.Community;
import com.noisy.rrssProject.model.entity.Moderator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityDTOMapper communityDTOMapper;

    public CommunityService(CommunityRepository communityRepository, CommunityDTOMapper communityDTOMapper) {
        this.communityRepository = communityRepository;
        this.communityDTOMapper = communityDTOMapper;
    }

    public CommunityResponse getCommunityByName(String name){
        return communityRepository.findByName(name).map(communityDTOMapper).orElseThrow(()->
                new CommunityNotFoundException("Community could not find by name: "+ name));
    }
    public Community findCommunityByName(String name){
        return communityRepository.findByName(name).orElseThrow(()->
                new CommunityNotFoundException("Community could not find by name: "+ name));
    }

    public List<CommunityResponse> getAllCommunities() {
        return communityRepository.findAll().stream().map(communityDTOMapper).collect(Collectors.toList());
    }

    public List<CommunityResponse> getAllCommunitiesByModeratorName(String moderatorName) {
        return communityRepository.findAll().stream()
                .filter(community -> containsModeratorWithName(community, moderatorName))
                .map(communityDTOMapper)
                .collect(Collectors.toList());
    }
    private boolean containsModeratorWithName(Community community, String moderatorName) {
        return community.getModerators().stream()
                .map(Moderator::getName)
                .anyMatch(name -> name.equals(moderatorName));
    }
    public Community createCommunity(CommunityRequest request) {
        Community community = communityDTOMapper.mapCommunityRequestToCommunity(request);
        return communityRepository.save(community);
    }

    public CommunityResponse updateCommunity(String name, CommunityRequest request) {
        Community community = communityRepository.findByName(name).orElseThrow();
        if (request.name() != null) community.setName(request.name());
        if (request.description() != null) community.setDescription(request.description());
        communityRepository.save(community);
        return communityDTOMapper.apply(community);
    }

    public void deleteCommunity(String name) {
        communityRepository.deleteByName(name);
    }



}
