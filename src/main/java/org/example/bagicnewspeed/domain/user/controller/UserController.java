package org.example.bagicnewspeed.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.annotation.Auth;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.user.dto.request.UpdatePasswordRequest;
import org.example.bagicnewspeed.domain.user.dto.request.UpdateUserRequest;
import org.example.bagicnewspeed.domain.user.dto.response.UserResponse;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/users/{nickName}/profile")
    public UserResponse userProfile(@PathVariable String nickName) {
        return userService.getProfile(nickName);
    }

    // 자기 자신만 변경이 가능해야한다 -> 인증이 필요하다
    @PatchMapping("/users/profile")
    public void updateUserProfile(@Auth AuthUser authUser, @RequestBody UpdateUserRequest request) {
        userService.updateProfile(authUser, request);
    }

    @PatchMapping("/users/profile/password")
    public void changePassword(@Auth AuthUser authUser, @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(authUser,request);
    }
}
