package org.example.bagicnewspeed.domain.auth.service;

import org.example.bagicnewspeed.common.jwt.JwtUtil;
import org.example.bagicnewspeed.common.resolver.PasswordEncoder;
import org.example.bagicnewspeed.domain.auth.dto.request.LoginRequest;
import org.example.bagicnewspeed.domain.auth.dto.request.SignupRequest;
import org.example.bagicnewspeed.domain.auth.dto.response.LoginResponse;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @Test
    void 회원가입() {
        // given
        SignupRequest signupRequest = new SignupRequest();
        ReflectionTestUtils.setField(signupRequest, "nickName", "test");
        ReflectionTestUtils.setField(signupRequest, "email", "test@email.com");
        ReflectionTestUtils.setField(signupRequest, "password", "1234Test");

        given(userRepository.existsByEmail("test@email.com"))
                .willReturn(false);
        given(passwordEncoder.encode(signupRequest.getPassword()))
                .willReturn("encodedPassword");
        given(userRepository.save(any(User.class)))
                .willReturn(new User("teat", "test@email.com", "encodedPassword"));
        // any뒤에 아무 것도 적지 않으면 테스트에서 생성된 user객체가 signupRequest에 기반하여 생성된 것과 일치하지 않아서 매칭이 안됨
        // 따라서 any뒤에 user타입에 대한 인자에만 매칭되게 진행

        // when
        authService.signup(signupRequest);
        // then
        verify(userRepository).existsByEmail("test@email.com");
        verify(passwordEncoder).encode("1234Test");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 로그인() {
        // given
        LoginRequest loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(loginRequest, "email", "test@email.com");
        ReflectionTestUtils.setField(loginRequest, "password", "1234Test");

        User user = new User("test", "test@email.com", "encodedPassword");

        String mockJwt = "bearer test";
        given(userRepository.findByEmail("test@email.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).willReturn(true);
        given(jwtUtil.createToken(user.getId(), loginRequest.getEmail())).willReturn(mockJwt);
        // when
        authService.login(loginRequest);
        // then
        verify(userRepository).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), user.getPassword());
        verify(jwtUtil).createToken(user.getId(), loginRequest.getEmail());
    }
}