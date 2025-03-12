package org.example.bagicnewspeed.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.common.resolver.PasswordEncoder;
import org.example.bagicnewspeed.common.jwt.JwtUtil;
import org.example.bagicnewspeed.domain.auth.dto.request.LoginRequest;
import org.example.bagicnewspeed.domain.auth.dto.request.SignupRequest;
import org.example.bagicnewspeed.domain.auth.dto.response.LoginResponse;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // jwtutil이 컴포넌트라 주입이 가능하다

    @Transactional
    public void signup(SignupRequest signupRequest) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        String password = passwordEncoder.encode(signupRequest.getPassword());

        User user = new User(
                signupRequest.getNickName(),
                signupRequest.getEmail(),
                password
        );
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String bearJwt = jwtUtil.createToken(user.getId(), loginRequest.getEmail());
        return new LoginResponse(bearJwt);
    }
}
