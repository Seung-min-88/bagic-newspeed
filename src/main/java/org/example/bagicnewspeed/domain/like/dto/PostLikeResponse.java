package org.example.bagicnewspeed.domain.like.dto;

import lombok.Getter;

@Getter
public class PostLikeResponse {

    private Long postId;
    private boolean liked;
    private int postLikeCount;

    public PostLikeResponse(Long postId, boolean liked, int postLikeCount) {
        this.postId = postId;
        this.liked = liked;
        this.postLikeCount = postLikeCount;
    }
}
