package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.PostRequest;
import com.noisy.rrssProject.model.dto.response.PostPageResponse;
import com.noisy.rrssProject.model.dto.response.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Post;
import com.noisy.rrssProject.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> responseList = postService.getAllPosts();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody PostRequest post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody PostRequest post) {
        PostResponse updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully.");
    }
    @GetMapping("/all/customer")
    public ResponseEntity<PostPageResponse> getAllPostsByUserName(
            @RequestParam(value = "page", required = false,defaultValue = "0") int page
            , @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ,@RequestParam String userName) {
        PostPageResponse responseList = postService.getPaginatedByUserName(page,size,userName);
        return ResponseEntity.ok(responseList);
    }
    @GetMapping("/all/community")
    public ResponseEntity<PostPageResponse> getAllPostsByCommunity(
            @RequestParam(value = "page", required = false,defaultValue = "0") int page
            , @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ,@RequestParam String community) {
        PostPageResponse responseList = postService.getPaginatedByCommunity(page,size,community);
        return ResponseEntity.ok(responseList);
    }


}
