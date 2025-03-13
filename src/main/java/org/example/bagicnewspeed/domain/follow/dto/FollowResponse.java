package org.example.bagicnewspeed.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowResponse {

    private String followerNickName;
    private String followingNickName;

    public FollowResponse(String followerNickName, String followingNickName) {
        this.followerNickName = followerNickName;
        this.followingNickName = followingNickName;
    }
}
