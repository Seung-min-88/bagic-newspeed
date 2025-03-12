package org.example.bagicnewspeed.domain.post.dto.response;

import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String nickName;
    private String title;
    private String content;

    public PostResponse(Long id, String nickName, String title, String content) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
    }
}
