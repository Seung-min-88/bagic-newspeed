package org.example.bagicnewspeed.domain.like.repository;

import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.example.bagicnewspeed.domain.like.entity.Like;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndComment(User user, Comment comment);
}
