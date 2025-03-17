package org.example.bagicnewspeed.domain.post.dto.response;

import lombok.Getter;

@Getter
public class PostResponse {

    private Long postId;
    private String nickName;
    private String title;
    private String content;
    private int likeCount;

    public PostResponse(Long postId, String nickName, String title, String content) {
        this.postId = postId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
    }

    public PostResponse(Long postId, String nickName, String title, String content, int likeCount) {
        this.postId = postId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
    }
}
