package org.example.bagicnewspeed.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponse {

    private Long postId;
    private Long commentId;
    private String nickName;
    private String comment;

    public CommentResponse(Long postId, Long commentId, String nickName, String comment) {
        this.postId = postId;
        this.commentId = commentId;
        this.nickName = nickName;
        this.comment = comment;
    }
}
