package org.example.bagicnewspeed.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bagicnewspeed.common.resolver.PasswordEncoder;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.user.dto.request.UpdatePasswordRequest;
import org.example.bagicnewspeed.domain.user.dto.request.UpdateUserRequest;
import org.example.bagicnewspeed.domain.user.dto.response.UserResponse;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public User getUser(AuthUser authUser) {
        return userRepository.findById(authUser.getId()).orElseThrow(
                ()-> new IllegalArgumentException("유저를 찾을 수 없습니다")
        );
    }

    @Transactional(readOnly = true)
    public User userInfo(String nickName) {
        return userRepository.findByNickName(nickName).orElseThrow(
                ()-> new IllegalArgumentException("유저를 찾을 수 없습니다 닉네임을 확인해주세요")
        );
    }

    @Transactional(readOnly = true)
    public UserResponse getProfile (String nickName) {
        User user = userRepository.findByNickName(nickName).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다")
        );
        return new UserResponse(user.getNickName(), user.getEmail());
    }

    @Transactional
    public void updateProfile (AuthUser authUser, UpdateUserRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저는 존재하지 않습니다")
        );
        // 닉네임 중복 확인
        if (userRepository.existsByNickName(request.getNickName())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        };
        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다");
        }
        user.updateUser(request.getNickName(), request.getEmail());
    }

    @Transactional
    public void updatePassword (AuthUser authUser, UpdatePasswordRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저는 존재하지 않습니다")
        );
        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 일치하지 않습니다");
        }
        // 기존 비밀번호와 다른지 확인
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다");
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


}
