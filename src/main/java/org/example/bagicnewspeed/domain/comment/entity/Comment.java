package org.example.bagicnewspeed.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bagicnewspeed.common.base.BaseEntity;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String content;

    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
