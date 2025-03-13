package org.example.bagicnewspeed.domain.like.repository;

import org.example.bagicnewspeed.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndLikeId(Long userId, Long likeId);
}
