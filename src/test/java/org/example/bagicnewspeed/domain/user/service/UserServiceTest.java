package org.example.bagicnewspeed.domain.user.service;

import org.example.bagicnewspeed.common.jwt.JwtUtil;
import org.example.bagicnewspeed.common.resolver.PasswordEncoder;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.user.dto.request.UpdatePasswordRequest;
import org.example.bagicnewspeed.domain.user.dto.request.UpdateUserRequest;
import org.example.bagicnewspeed.domain.user.dto.request.UserRequest;
import org.example.bagicnewspeed.domain.user.dto.response.UserResponse;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void 유저_아이디로_유저를_가져온다() {
        // given
        AuthUser authUser = new AuthUser(1L, "닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com", "encodedPassword");
        ReflectionTestUtils.setField(user, "id", 1L);
        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));

        // when
        User finduser = userService.getUser(authUser);
        // then
        assertThat(finduser).isNotNull();
        assertThat(finduser.getId()).isEqualTo(authUser.getId());
    }

    @Test
    void 닉네임으로_유저를_가져온다() {
        // given
        User user = new User("닉네임", "test@email.com", "encodedPassword");
        given(userRepository.findByNickName(user.getNickName())).willReturn(Optional.of(user));
        // when
        User findNickUser = userService.userInfo(user.getNickName());
        // then
        assertThat(findNickUser).isNotNull();
        assertThat(findNickUser.getNickName()).isEqualTo(user.getNickName());
    }

    @Test
    void 유저_프로필을_조회한다() {
        // given
        User user = new User("닉네임", "test@email.com", "encodedPassword");
        given(userRepository.findByNickName(user.getNickName())).willReturn(Optional.of(user));
        // when
        UserResponse findUser = userService.getProfile(user.getNickName());
        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getNickName()).isEqualTo(user.getNickName());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void 유저_정보_수정을_할_수_있다() {
        // given
        AuthUser authUser = new AuthUser(1L, "닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com", "encodedPassword");

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        ReflectionTestUtils.setField(updateUserRequest, "nickName", "수정된 닉네임");
        ReflectionTestUtils.setField(updateUserRequest, "email", "update@email.com");

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));
        given(userRepository.existsByNickName(updateUserRequest.getNickName())).willReturn(false);
        given(userRepository.existsByEmail(updateUserRequest.getEmail())).willReturn(false);
        // when
        userService.updateProfile(authUser, updateUserRequest);
        // then
        assertThat(user.getNickName()).isEqualTo(updateUserRequest.getNickName());
        assertThat(user.getEmail()).isEqualTo(updateUserRequest.getEmail());
    }

    @Test
    void 유저의_비밀번호를_수정_할_수_있다() {
        // given
        AuthUser authUser = new AuthUser(1L, "닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com", "encodedPassword");

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        ReflectionTestUtils.setField(updatePasswordRequest, "oldPassword", "oldPassword");
        ReflectionTestUtils.setField(updatePasswordRequest, "newPassword", "newPassword");

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(updatePasswordRequest.getOldPassword(),user.getPassword())).willReturn(true);
        given(passwordEncoder.matches(updatePasswordRequest.getNewPassword(),user.getPassword())).willReturn(false);
        given(passwordEncoder.encode(updatePasswordRequest.getNewPassword())).willReturn("newPassword");
        // when
        userService.updatePassword(authUser, updatePasswordRequest);
        // then
        assertThat(user.getPassword()).isEqualTo(updatePasswordRequest.getNewPassword());
    }
}