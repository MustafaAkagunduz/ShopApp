package com.noisy.rrssProject.model.dto.mapper;

import com.noisy.rrssProject.model.dto.request.CommentRequest;
import com.noisy.rrssProject.model.dto.response.CommentResponse;
import com.noisy.rrssProject.model.entity.Comment;
import com.noisy.rrssProject.service.CustomerService;
import com.noisy.rrssProject.service.PostService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentResponse> {

    private final CustomerService customerService;
    private final PostService postService;

    public CommentDTOMapper(CustomerService customerService, PostService postService) {
        this.customerService = customerService;
        this.postService = postService;
    }

    @Override
    public CommentResponse apply(Comment comment) {
        return new CommentResponse(comment.getId(),comment.getCommentDate(),comment.getContent());
    }

    public Comment CommentRequestToComment(CommentRequest request){
        Comment comment = new Comment();
        comment.setPost(postService.findPostById(request.postId()));
        comment.setCustomer(customerService.findCustomerById(request.userId()));
        comment.setContent(request.content());
        comment.setCommentDate(LocalDateTime.now());
        return comment;
    }
}
