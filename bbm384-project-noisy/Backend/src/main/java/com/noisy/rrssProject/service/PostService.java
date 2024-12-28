package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.PostNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.PostDTOMapper;
import com.noisy.rrssProject.model.dto.request.PostRequest;
import com.noisy.rrssProject.model.dto.response.PostPageResponse;
import com.noisy.rrssProject.model.dto.response.PostResponse;
import com.noisy.rrssProject.repository.PostRepository;
import com.noisy.rrssProject.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostDTOMapper mapper;
    private final PostDTOMapper postDTOMapper;

    public PostService(PostRepository postRepository, PostDTOMapper mapper, PostDTOMapper postDTOMapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.postDTOMapper = postDTOMapper;
    }

    public PostResponse getPostById(Long id){
        return postRepository.findById(id).map(mapper).orElseThrow(()->
                new PostNotFoundException("Post could not find by id: "+ id));
    }
    public Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(()->
                new PostNotFoundException("Post could not find by id: "+ id));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(mapper).
                collect(Collectors.toList());
    }

    public Post createPost(PostRequest req) {

        return postRepository.save(mapper.PostRequestToPost(req));
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = mapper.PostRequestToPost(request);
        post.setId(id); // Assuming id is set in the post object
        return mapper.apply(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public PostPageResponse getPaginatedByUserName(int pageNo, int size, String UserName) {
        Pageable pageable = PageRequest.of(pageNo, size);

        Page<Post> page = postRepository.findAll(pageable);

        List<PostResponse> content = page.getContent().stream()
                .filter(post -> post.getCustomer().getName().equals(UserName))
                .map(postDTOMapper)
                .collect(Collectors.toList());

        return getProductPageResponse(content, page);

    }



    private PostPageResponse getProductPageResponse(List<PostResponse> content, Page<Post> page) {
        PostPageResponse postPageResponse = new PostPageResponse();
        postPageResponse.setContent(content);
        postPageResponse.setPageNo(page.getNumber());
        postPageResponse.setPageSize(page.getSize());
        postPageResponse.setTotalElements(page.getTotalElements());
        postPageResponse.setTotalPages(page.getTotalPages());
        postPageResponse.setLast(page.isLast());
        return postPageResponse;
    }

    public PostPageResponse getPaginatedByCommunity(int pageNo, int size, String community) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Post> page = postRepository.findAll(pageable);

        List<PostResponse> content = page.getContent().stream()
                .filter(post -> post.getCommunity().getName().equals(community))
                .map(postDTOMapper)
                .collect(Collectors.toList());

        return getProductPageResponse(content, page);
    }
}
