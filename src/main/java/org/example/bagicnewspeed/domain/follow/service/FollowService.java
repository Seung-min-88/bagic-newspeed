package org.example.bagicnewspeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.follow.dto.FollowResponse;
import org.example.bagicnewspeed.domain.follow.entity.Follow;
import org.example.bagicnewspeed.domain.follow.enums.FollowStatus;
import org.example.bagicnewspeed.domain.follow.repository.FollowRepository;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    @Transactional
    public void connectFollow(String followerNickname, String followingNickname) {
        User following = userService.userInfo(followingNickname);
        User follower = userService.userInfo(followerNickname);
        Follow follow = new Follow(following, follower, FollowStatus.FOLLOWER);
        followRepository.save(follow);
    }

    @Transactional
    public void  disconnectFollow(String followerNickname, String followingNickname) {
        User following = userService.userInfo(followingNickname);
        User follower = userService.userInfo(followerNickname);
        Follow follow = followRepository.findByFollowerAndFollowing(follower,following).orElseThrow(
                ()-> new IllegalArgumentException("팔로우 관계가 아닙니다")
        );
        followRepository.delete(follow);
    }

    @Transactional
    public List<FollowResponse> followList(AuthUser authUser) {
        List<Follow> follows = followRepository.findAllByFollower(authUser);
        return follows.stream().map(follow -> new FollowResponse(
                follow.getFollower().getNickName(),
                follow.getFollowing().getNickName())
        ).collect(Collectors.toList());
    }
}
