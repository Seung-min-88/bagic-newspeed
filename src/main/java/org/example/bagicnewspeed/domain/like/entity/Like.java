package org.example.bagicnewspeed.domain.like.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id",nullable = false)
    private Comment comment;

    private boolean liked;

    public Like(User user, Post post, boolean liked) {
        this.user = user;
        this.post = post;
        this.liked = liked;
    }

    public Like(User user, Post post, Comment comment, boolean liked) {
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.liked = liked;
    }
}
