package org.example.bagicnewspeed.domain.follow.repository;

import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.follow.entity.Follow;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findAllByFollower(AuthUser authUser);
    List<Follow> findAllByFollowing(User following);
}
