package org.example.bagicnewspeed.domain.post.repository;

import org.example.bagicnewspeed.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
