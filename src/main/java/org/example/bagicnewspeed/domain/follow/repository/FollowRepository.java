package org.example.bagicnewspeed.domain.follow.repository;

import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.follow.entity.Follow;
import org.example.bagicnewspeed.domain.follow.enums.FollowStatus;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
//    Optional<Follow> findByFollower(User follower);
//    Optional<Follow> findByFollowing(User following);
    List<Follow> findAllByFollowerAndStatus(User follower, FollowStatus status);
    List<Follow> findAllByFollowingAndStatus(User following, FollowStatus status);
}
