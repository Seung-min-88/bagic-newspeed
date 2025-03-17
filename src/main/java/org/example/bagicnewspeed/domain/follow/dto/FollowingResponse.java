package org.example.bagicnewspeed.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowingResponse {

    private String followingNickName;

    public FollowingResponse(String followingNickName) {
        this.followingNickName = followingNickName;
    }
}
