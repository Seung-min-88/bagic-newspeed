package org.example.bagicnewspeed.domain.follow.service;

import org.example.bagicnewspeed.domain.follow.entity.Follow;
import org.example.bagicnewspeed.domain.follow.enums.FollowStatus;
import org.example.bagicnewspeed.domain.follow.repository.FollowRepository;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private FollowService followService;

    @Test
    void 팔로우를_한다() {
        // givne
        User followerUser = new User("팔로워" , "follower@email.com", "123456");
        User followingUser = new User("팔로잉", "following@email.com", "123456");

        given(userService.userInfo(followerUser.getNickName())).willReturn(followerUser);
        given(userService.userInfo(followingUser.getNickName())).willReturn(followingUser);

        Follow follower = new Follow(followingUser, followerUser, FollowStatus.FOLLOWER);
        Follow following = new Follow(followerUser, followingUser, FollowStatus.FOLLOWING);

        followRepository.save(follower);
        followRepository.save(following);
        // when
        followService.connectFollow("팔로워", "팔로잉");
        // then
        assertThat(follower.getFollowStatus()).isEqualTo(FollowStatus.FOLLOWER);
        assertThat(following.getFollowStatus()).isEqualTo(FollowStatus.FOLLOWING);
    }

    @Test
    void 언팔로우를_한다() {
        // given
        User followerUser = new User("팔로워" , "follower@email.com", "123456");
        User followingUser = new User("팔로잉", "following@email.com", "123456");

        Follow follower = new Follow(followingUser, followerUser, FollowStatus.FOLLOWER);
        ReflectionTestUtils.setField(follower, "followId", 1L);

        given(userService.userInfo("팔로워")).willReturn(followerUser);
        given(userService.userInfo("팔로잉")).willReturn(followingUser);
        given(followRepository.findByFollowerAndFollowing(followerUser, followingUser)).willReturn(Optional.of(follower));

        // when
        followService.disconnectFollow("팔로워", "팔로잉");
        // then
        verify(followRepository).delete(follower);

    }
}