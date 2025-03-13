package org.example.bagicnewspeed.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.example.bagicnewspeed.domain.comment.service.CommentService;
import org.example.bagicnewspeed.domain.like.entity.Like;
import org.example.bagicnewspeed.domain.like.repository.LikeRepository;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.post.service.PostService;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public void likePost(AuthUser authUser, Long postId) {
        User user = userService.userInfo(authUser.getNickName());
        Post post = postService.postInfo(postId);

        if (user.getId() == post.getUser().getId()) {
            throw new IllegalArgumentException("자신의 게시물에 좋아요를 누를 수 없습니다");
        }

        boolean isLiked;
        if (likeRepository.existsByUserIdAndLikeId(user.getId(), postId)) {
            post.decreaseLikeCount();
            isLiked = false;
        } else {
            post.increaseLikeCount();
            isLiked = true;
        }

        Like like = new Like(user, post, isLiked);
        likeRepository.save(like);
    }

    @Transactional
    public void likeComment(AuthUser authUser,Long postId ,Long commentId) {
        User user = userService.userInfo(authUser.getNickName());
        Post post = postService.postInfo(postId);
        Comment comment = commentService.commentInfo(commentId);

        if(user.getId() == comment.getUser().getId()){
            throw new IllegalArgumentException("자신의 댓글에 좋아요를 누를 수 없습니다");
        }

        boolean isLiked;
        if (likeRepository.existsByUserIdAndLikeId(user.getId(), commentId)) {
            comment.decreaseLikeCount();
            isLiked = false;
        } else {
            comment.increaseLikeCount();
            isLiked = true;
        }

        Like like = new Like(user, post, comment, isLiked);
        likeRepository.save(like);
    }
}
