package org.example.bagicnewspeed.domain.post.dto.response;

import lombok.Getter;

@Getter
public class PostResponse {

    private Long postId;
    private Long userId;
    private String title;
    private String content;

    public PostResponse(Long postId, Long userId, String title, String content) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
