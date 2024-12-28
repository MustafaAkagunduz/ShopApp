package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.CommentNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.CommentDTOMapper;
import com.noisy.rrssProject.model.dto.request.CommentRequest;
import com.noisy.rrssProject.model.dto.response.CommentResponse;
import com.noisy.rrssProject.repository.CommentRepository;
import com.noisy.rrssProject.model.entity.Comment;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDTOMapper mapper;

    public CommentService(CommentRepository commentRepository, CommentDTOMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    public CommentResponse getCommentById(Long id){
        return commentRepository.findById(id).map(mapper).orElseThrow(()-> new
                CommentNotFoundException("Comment could not find by id: "+id));
    }
    public Comment findCommentById(Long id){
        return commentRepository.findById(id).orElseThrow(()-> new
                CommentNotFoundException("Comment could not find by id: "+id));
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    public Comment createComment(CommentRequest request) {
        return commentRepository.save(mapper.CommentRequestToComment(request));
    }

    public CommentResponse updateComment(Long id, CommentRequest request) {
        Comment comment = mapper.CommentRequestToComment(request);
        comment.setId(id);
        return mapper.apply(commentRepository.save(comment));
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<CommentResponse> getAllCommentsFromPost(Long postId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .map(mapper).collect(Collectors.toList());
    }
}
