package org.example.bagicnewspeed.domain.comment.repository;

import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
