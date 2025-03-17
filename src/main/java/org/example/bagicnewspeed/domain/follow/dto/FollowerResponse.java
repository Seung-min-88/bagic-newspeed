package org.example.bagicnewspeed.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowerResponse {

    private String followerNickname;

    public FollowerResponse(String followerNickname) {
        this.followerNickname = followerNickname;
    }
}
