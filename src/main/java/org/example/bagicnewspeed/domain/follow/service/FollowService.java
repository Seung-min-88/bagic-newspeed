package org.example.bagicnewspeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.follow.dto.FollowResponse;
import org.example.bagicnewspeed.domain.follow.dto.FollowerResponse;
import org.example.bagicnewspeed.domain.follow.dto.FollowingResponse;
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

    // 팔로우
    @Transactional
    public void connectFollow(String followerNickname, String followingNickname) {
        User followingUser = userService.userInfo(followingNickname); //
        User followerUser = userService.userInfo(followerNickname);
        Follow follower = new Follow(followingUser, followerUser, FollowStatus.FOLLOWER);
        Follow following = new Follow(followerUser,followingUser, FollowStatus.FOLLOWING);

        followRepository.save(follower);
        followRepository.save(following);
    }

    // 언팔
    @Transactional
    public void disconnectFollow(String followerNickname, String followingNickname) {
        User following = userService.userInfo(followingNickname);
        User follower = userService.userInfo(followerNickname);
        Follow follow = followRepository.findByFollowerAndFollowing(follower,following).orElseThrow(
                ()-> new IllegalArgumentException("팔로우 관계가 아닙니다")
        );
        followRepository.delete(follow);
    }

    // 팔로워 정보
    @Transactional
    public Follow followerInfo(String followerNickname) {
        User follower = userService.userInfo(followerNickname);
        return followRepository.findByFollower(follower).orElseThrow(
                ()-> new IllegalArgumentException("팔로워가 없습니다")
        );
    }
    // 팔로잉 정보
    @Transactional
    public Follow followingInfo(String followingNickname) {
        User following = userService.userInfo(followingNickname);
        return followRepository.findByFollowing(following).orElseThrow(
                ()-> new IllegalArgumentException("팔로잉하는 사람이 없습니다")
        );
    }

    // 팔로워 리스트
    @Transactional(readOnly = true)
    public List<FollowerResponse> followerList(AuthUser authUser) {
        User user = userService.getUser(authUser);
        List<Follow> follows = followRepository.findAllByFollowing(user);
        return follows.stream().map(follow -> new FollowerResponse(
                follow.getFollower().getNickName()
        )).collect(Collectors.toList());
    }

    // 팔로잉 리스트
    @Transactional(readOnly = true)
    public List<FollowingResponse> followingList(AuthUser authUser) {
        User user = userService.getUser(authUser);
        List<Follow> follows = followRepository.findAllByFollower(user);
        return follows.stream().map(follow -> new FollowingResponse(
                follow.getFollowing().getNickName()
        )).collect(Collectors.toList());
    }

//    public List<Follow> FollowerList(AuthUser authUser) {
//        User user = userService.getUser(authUser);
//        return followRepository.findAllByFollower(user);
//    }
}
