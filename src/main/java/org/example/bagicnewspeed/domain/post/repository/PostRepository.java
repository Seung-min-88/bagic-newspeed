package org.example.bagicnewspeed.domain.post.repository;

import org.example.bagicnewspeed.domain.follow.entity.Follow;
import org.example.bagicnewspeed.domain.post.dto.response.PostResponse;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    SELECT p FROM Post p
    JOIN Follow f ON p.user.id = f.following.id
    WHERE p.user.id = :userId
    AND f.followStatus = 'FOLLOWING'
    ORDER BY p.updatedAt DESC
    """)
    Page<Post> findAllByFollowing(@Param("userid") Long userId, Pageable pageable);

    Page<PostResponse> findAllPostByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
