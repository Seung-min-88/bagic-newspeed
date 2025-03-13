package org.example.bagicnewspeed.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.follow.dto.FollowResponse;
import org.example.bagicnewspeed.domain.follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 내가 팔로우를 하는 경우
    @PostMapping("/follow")
    public void follow(@RequestParam String followerNickName, @RequestParam String followingNickName) {
        followService.connectFollow(followingNickName, followerNickName);
    }

    // 언팔로우
    @DeleteMapping("/follow")
    public void unfollow(@RequestParam String followerNickName, @RequestParam String followingNickName) {
        followService.disconnectFollow(followingNickName,followerNickName);
    }

    // 팔로우 리스트
    @GetMapping("/follow/List")
    public List<FollowResponse> followList(@Auth AuthUser authUser) {
        List<FollowResponse> follows = followService.followList(authUser);
        return follows;
    }
}
