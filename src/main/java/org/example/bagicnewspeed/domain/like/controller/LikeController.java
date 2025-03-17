package org.example.bagicnewspeed.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.like.dto.CommentLikeResponse;
import org.example.bagicnewspeed.domain.like.dto.PostLikeResponse;
import org.example.bagicnewspeed.domain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 게시물에 좋아요
    @PostMapping("/posts/{postId}/likes")
    public PostLikeResponse likePost(@Auth AuthUser authUser, @PathVariable Long postId){
        return likeService.likePost(authUser, postId);
    }

    // 댓글에 좋아요
    @PostMapping("/posts/{postId}/comments/{commentId}/likes")
    public CommentLikeResponse likeComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId){
        return likeService.likeComment(authUser, postId, commentId);
    }

}
