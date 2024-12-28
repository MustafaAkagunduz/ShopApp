package com.noisy.rrssProject.model.dto.mapper;

import com.noisy.rrssProject.model.dto.request.PostRequest;
import com.noisy.rrssProject.model.dto.response.CommentResponse;
import com.noisy.rrssProject.model.dto.response.PostResponse;
import com.noisy.rrssProject.model.entity.Post;
import com.noisy.rrssProject.service.CommunityService;
import com.noisy.rrssProject.service.CustomerService;
import org.springframework.stereotype.Service;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostDTOMapper implements Function<Post, PostResponse> {

    private final CustomerService customerService;
    private final CommunityService communityService;

    public PostDTOMapper(CustomerService customerService, CommunityService communityService) {
        this.customerService = customerService;
        this.communityService = communityService;
    }

    @Override
    public PostResponse apply(Post post) {
        return new PostResponse(
                post.getId(),
                post.getCustomer().getName(),
                post.getTitle(),
                post.getContent(),
                post.getPostDate(),
                post.getComments().stream().map(comment -> new CommentResponse(comment.getId(),comment.getCommentDate(),comment.getContent())).collect(Collectors.toList()));
    }

    public Post PostRequestToPost(PostRequest request){
        return new Post(null,customerService.findCustomerById(
                request.customerId()),
                request.title(),
                request.content(),
                request.postDate(),communityService.findCommunityByName(request.community()),null);
    }

}