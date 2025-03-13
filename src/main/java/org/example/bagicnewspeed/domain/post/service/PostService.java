package org.example.bagicnewspeed.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.post.dto.request.PostRequest;
import org.example.bagicnewspeed.domain.post.dto.response.PostResponse;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.post.repository.PostRepository;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponse createPost(AuthUser authUser, PostRequest postRequest) {
        User user = userService.getUser(authUser);
        Post post = new Post(
                user,
                postRequest.getTitle(),
                postRequest.getContent()
        );
        postRepository.save(post);
        return new PostResponse(
                post.getPostId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContent());
    }

    @Transactional
    public PostResponse updatePost(AuthUser authUser,Long postId, PostRequest postRequest) {
        User user = userService.getUser(authUser);
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다")
        );
        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("작성자만 게시물을 수정할 수 있습니다");
        }
        post.update(
                postRequest.getTitle(),
                postRequest.getContent()
        );
        return mapToResponse(post);
    }

    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        User user = userService.getUser(authUser);
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다")
        );
        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("작성자만 게시물을 수정할 수 있습니다");
        }
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::mapToResponse);

    }

    @Transactional(readOnly = true)
    public Post postInfo(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다")
        );
    }

    private PostResponse mapToResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getContent()
        );
    }
}
