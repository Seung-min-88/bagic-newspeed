package org.example.bagicnewspeed.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.like.dto.PostLikeResponse;
import org.example.bagicnewspeed.domain.like.entity.Like;
import org.example.bagicnewspeed.domain.like.service.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 게시물에 좋아요
    @PostMapping("/post/{postId}/like")
    public void likePost(@Auth AuthUser authUser, @PathVariable Long postId){
        likeService.likePost(authUser, postId);
    }

    // 댓글에 좋아요
    @PostMapping("/post/{postId}/comment/{commentId}/like")
    public void likeComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId){
        likeService.likeComment(authUser, postId, commentId);
    }

}
