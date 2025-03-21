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
        User followerUser = userService.userInfo(followerNickname); // 팔로우 하는 사람
        User followingUser = userService.userInfo(followingNickname); // 팔로우 당하는 사람

        Follow follower = new Follow(followingUser, followerUser, FollowStatus.FOLLOWING);
        Follow following = new Follow(followerUser,followingUser, FollowStatus.FOLLOWER);

        followRepository.save(follower);
        followRepository.save(following);
    }

    // 언팔
    @Transactional
    public void disconnectFollow(String followerNickname, String followingNickname) {
        User followerUser = userService.userInfo(followerNickname); // 팔로우 하는 사람
        User followingUser = userService.userInfo(followingNickname); // 팔로우 당한 사람

        Follow follower = followRepository.findByFollowerAndFollowing(followerUser,followingUser).orElseThrow(
                ()-> new IllegalArgumentException("팔로우 관계가 아닙니다")
        );
        Follow following = followRepository.findByFollowerAndFollowing(followingUser,followerUser).orElseThrow(
                ()-> new IllegalArgumentException("팔로우 관계가 아닙니다")
        );
        followRepository.delete(follower);
        followRepository.delete(following);
    }

    // 만들긴 했으나 사용안 할 것 같아 주석처리
//    // 팔로워 정보
//    @Transactional
//    public Follow followerInfo(String followerNickname) {
//        User follower = userService.userInfo(followerNickname);
//        return followRepository.findByFollower(follower).orElseThrow(
//                ()-> new IllegalArgumentException("팔로워가 없습니다")
//        );
//    }
//    // 팔로잉 정보
//    @Transactional
//    public Follow followingInfo(String followingNickname) {
//        User following = userService.userInfo(followingNickname);
//        return followRepository.findByFollowing(following).orElseThrow(
//                ()-> new IllegalArgumentException("팔로잉하는 사람이 없습니다")
//        );
//    }

    // 팔로워 리스트 (나를 팔로우 한 사람 조회)
    @Transactional(readOnly = true)
    public List<FollowerResponse> followerList(AuthUser authUser) {
        User user = userService.getUser(authUser);

        // 내가 FOLLOWER 로 등록된 사람을 조회 (나를 팔로우한 사람들)
        List<Follow> followList = followRepository.findAllByFollowerAndStatus(user, FollowStatus.FOLLOWER);
        return  followList.stream()
                .map(follow -> new FollowerResponse(follow.getFollower().getNickName()))
                .collect(Collectors.toList());
    }

    // 팔로잉 리스트 (내가 팔로우한 사람 조회)
    @Transactional(readOnly = true)
    public List<FollowingResponse> followingList(AuthUser authUser) {
        User user = userService.getUser(authUser);

        // 내가 FOLLOWING 으로 등록한 사람을 조회 (내가 팔로우한 사람들)
        List<Follow> followList = followRepository.findAllByFollowingAndStatus(user, FollowStatus.FOLLOWING);
        return followList.stream()
                .map(follow -> new FollowingResponse(follow.getFollowing().getNickName()))
                .collect(Collectors.toList());
    }
}
