package org.example.bagicnewspeed.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowResponse {

    private String FollowerNickName;
    private String FollowingNickName;

    public FollowResponse(String followerNickName, String followingNickName) {
        FollowerNickName = followerNickName;
        FollowingNickName = followingNickName;
    }
}
