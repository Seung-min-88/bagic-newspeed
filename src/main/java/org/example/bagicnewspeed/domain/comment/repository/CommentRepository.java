package org.example.bagicnewspeed.domain.comment.repository;

import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_Id(Long postId);
//    List<Comment> findByComment_id(Long id);
}
