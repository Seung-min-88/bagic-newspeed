package org.example.bagicnewspeed.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.follow.dto.FollowerResponse;
import org.example.bagicnewspeed.domain.follow.dto.FollowingResponse;
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

    // 팔로워 리스트 (나를 팔로우 하는 사람)
    @GetMapping("/follower/list")
    public List<FollowerResponse> followList(@Auth AuthUser authUser) {
        List<FollowerResponse> followers = followService.followerList(authUser);
        return followers;
    }

    // 팔로잉 리스트 (내가 팔로우한 사람)
    @GetMapping("/following/list")
    public List<FollowingResponse> followingList(@Auth AuthUser authUser) {
        List<FollowingResponse> followings = followService.followingList(authUser);
        return followings;
    }
}
