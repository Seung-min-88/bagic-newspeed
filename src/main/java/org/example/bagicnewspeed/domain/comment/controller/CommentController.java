package org.example.bagicnewspeed.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.comment.dto.CommentRequest;
import org.example.bagicnewspeed.domain.comment.dto.CommentResponse;
import org.example.bagicnewspeed.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 게시물에 댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public CommentResponse postComment(@Auth AuthUser authUser, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        return commentService.postComment(authUser ,postId, commentRequest);
    }

    // 게시글 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponse> getCommentsByPost(@Auth AuthUser authUser, @PathVariable Long postId) {
        return commentService.getPostComments(authUser, postId);
    }

    // 댓글수정
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponse updateComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(authUser,postId, commentId, commentRequest);
    }

    // 댓글삭제
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public void deleteComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(authUser, postId, commentId);
    }
}