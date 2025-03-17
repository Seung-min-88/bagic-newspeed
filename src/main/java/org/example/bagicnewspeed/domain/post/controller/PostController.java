package org.example.bagicnewspeed.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.post.dto.request.PostRequest;
import org.example.bagicnewspeed.domain.post.dto.response.PostResponse;
import org.example.bagicnewspeed.domain.post.repository.PostRepository;
import org.example.bagicnewspeed.domain.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 게시물 생성
    @PostMapping("/posts")
    public PostResponse createPost(@Auth AuthUser authUser, @RequestBody PostRequest postRequest) {
        return postService.createPost(authUser, postRequest);
    }

    // 게시물 수정
    @PutMapping("/posts/{postId}")
    public PostResponse updatePost(@Auth AuthUser authUser,@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return postService.updatePost(authUser, postId, postRequest);
    }

    //게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@Auth AuthUser authUser, @PathVariable Long postId) {
        postService.deletePost(authUser, postId);
    }

    // 생성일 기준 게시물 조회
    @GetMapping("/posts/all/create")
    public Page<PostResponse> getAllPostsByCreate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostResponse> posts = postService.getAllPosts(pageable);
        return posts;
    }

    // 수정일 기준 게시물 조회
    @GetMapping("/posts/all/update")
    public Page<PostResponse> getAllPostsByUpdate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<PostResponse> posts = postService.getAllPosts(pageable);
        return posts;
    }

    // 뉴스피드 팔로잉 게시물 최신순으로 보기
    @GetMapping("/posts/following")
    public Page<PostResponse> getAllPostsByFollowing(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<PostResponse> posts = postService.findAllFollowsPosts(authUser, pageable);
        return posts;
    }

    // 기간별 검색
    @GetMapping("/posts/date")
    public Page<PostResponse> getAllPostsByDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<PostResponse> posts = postService.findAllByDate(startDate, endDate, pageable);

        return posts;
    }
}
