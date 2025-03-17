package org.example.bagicnewspeed.domain.like.dto;

import lombok.Getter;

@Getter
public class CommentLikeResponse {

    private Long postId;
    private Long commentId;
    private boolean liked;
    private int commentLikeCount;

    public CommentLikeResponse(Long postId, Long commentId, boolean liked, int commentLikeCount) {
        this.postId = postId;
        this.commentId = commentId;
        this.liked = liked;
        this.commentLikeCount = commentLikeCount;
    }
}
