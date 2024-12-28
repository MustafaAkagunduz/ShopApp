package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.CommentRequest;
import com.noisy.rrssProject.model.dto.response.CommentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Comment;
import com.noisy.rrssProject.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        CommentResponse response = commentService.getCommentById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        List<CommentResponse> responseList = commentService.getAllComments();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/all/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsFromPost(@PathVariable Long postId) {
        List<CommentResponse> responseList = commentService.getAllCommentsFromPost(postId);
        return ResponseEntity.ok(responseList);
    }



    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest comment) {
        CommentResponse updatedComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully.");
    }
}
