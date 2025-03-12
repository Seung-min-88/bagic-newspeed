package org.example.bagicnewspeed.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostRequest {
    private Long postId;
    private String nickName;
    private String title;
    private String content;
}
