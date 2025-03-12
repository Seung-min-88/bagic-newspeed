package org.example.bagicnewspeed.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.post.dto.request.PostRequest;
import org.example.bagicnewspeed.domain.post.dto.response.PostResponse;
import org.example.bagicnewspeed.domain.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public PostResponse createPost(@Auth AuthUser authUser, @RequestBody PostRequest postRequest) {
        return postService.createPost(authUser, postRequest);
    }

    // 게시물 수정
    @PutMapping("/post/{postId}")
    public PostResponse updatePost(@Auth AuthUser authUser,@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return postService.updatePost(authUser, postId, postRequest);
    }

    //게시물 삭제
    @DeleteMapping("/post/{postId}")
    public void deletePost(@Auth AuthUser authUser, @PathVariable Long postId) {
        postService.deletePost(authUser, postId);
    }

    @GetMapping("/post/all")
    public Page<PostResponse> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostResponse> posts = postService.getAllPosts(pageable);
        return posts;
    }




}
