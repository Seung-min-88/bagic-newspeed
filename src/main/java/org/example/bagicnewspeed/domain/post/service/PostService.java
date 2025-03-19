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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    // 게시물 생성
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
                post.getId(),
                post.getUser().getNickName(),
                post.getTitle(),
                post.getContent());
    }

    // 게시물 수정
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

    // 게시물 삭제
    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        User user = userService.getUser(authUser);
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다")
        );
        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("작성자만 게시물을 삭제할 수 있습니다");
        }
        postRepository.delete(post);
    }

    // 모든 게시물 조회
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(this::mapToResponse);
    }

    // 팔로워 게시물 최신순으로
    @Transactional(readOnly = true)
    public Page<PostResponse> findAllFollowsPosts(AuthUser authUser, Pageable pageable) {
        User user = userService.getUser(authUser);
//        Follow follow = followService.followingInfo(authUser.getNickName());
        Page<Post> posts = postRepository.findAllByFollowing(user.getId(), pageable);
        return posts.map(this::mapToResponse);
//        return postRepository.findAllByFollowing(pageable);
    }

    // 날짜로 검색
    @Transactional(readOnly = true)
    public Page<PostResponse> findAllByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<Post> posts = postRepository.findAllPostByDate(startDate,endDate, pageable);
//        return postRepository.findAllPostByDate(startDate, endDate, pageable);
        return posts.map(this::mapToResponse);
    }


    @Transactional(readOnly = true)
    public Post postInfo(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다")
        );
    }

    private PostResponse mapToResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getUser().getNickName(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount()
        );
    }
}
