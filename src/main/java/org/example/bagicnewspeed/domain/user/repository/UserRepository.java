package org.example.bagicnewspeed.domain.user.repository;

import org.example.bagicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickName(String nickName);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    Optional<User> findByEmail(String email);
//    List<User> findByUserId(Long userId);
}
