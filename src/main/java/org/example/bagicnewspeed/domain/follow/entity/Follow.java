package org.example.bagicnewspeed.domain.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bagicnewspeed.domain.follow.enums.FollowStatus;
import org.example.bagicnewspeed.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // 나를 팔로우 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    // 내가 팔로우 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowStatus followStatus;

    public Follow(User follower, User following, FollowStatus followStatus) {
        this.follower = follower;
        this.following = following;
        this.followStatus = followStatus;
    }
}
