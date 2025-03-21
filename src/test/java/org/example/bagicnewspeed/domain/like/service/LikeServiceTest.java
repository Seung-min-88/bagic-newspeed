package org.example.bagicnewspeed.domain.like.service;

import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.example.bagicnewspeed.domain.comment.service.CommentService;
import org.example.bagicnewspeed.domain.like.dto.CommentLikeResponse;
import org.example.bagicnewspeed.domain.like.dto.PostLikeResponse;
import org.example.bagicnewspeed.domain.like.entity.Like;
import org.example.bagicnewspeed.domain.like.repository.LikeRepository;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.post.service.PostService;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private UserService userService;
    @Mock
    private PostService postService;
    @Mock
    private CommentService commentService;
    @InjectMocks
    private LikeService likeService;

    @Test
    void 게시물에_좋아요를_누를수_있다() {
        // given
        AuthUser authUser = new AuthUser(2L, "닉네임" , "test@email.com");
        User user = new User("닉네임", "test@email.com", "1234Test");
        ReflectionTestUtils.setField(user, "id", 1L);
        User user2 = new User("테스터", "tester@email.com", "1234Tester");
        ReflectionTestUtils.setField(user2, "id", 2L);

        Post post = new Post(user, "테스트 제목", "테스트 내용");
        ReflectionTestUtils.setField(post, "id", 1L);
        ReflectionTestUtils.setField(post, "likeCount", 0);

        given(userService.getUser(authUser)).willReturn(user2);
        given(postService.postInfo(1L)).willReturn(post);
        given(likeRepository.findByUserAndPost(user2, post)).willReturn(Optional.empty());
        // when
        PostLikeResponse response = likeService.likePost(authUser, 1L);
        // then
        assertThat(response.isLiked()).isTrue();
        assertThat(response.getPostLikeCount()).isEqualTo(1);
        verify(likeRepository).save(any(Like.class));
    }

    @Test
    void 게시물_좋아요를_취소한다() {
        // given
        AuthUser authUser = new AuthUser(2L, "닉네임" , "test@email.com");
        User user = new User("닉네임", "test@email.com", "1234Test");
        ReflectionTestUtils.setField(user, "id", 1L);
        User user2 = new User("테스터", "tester@email.com", "1234Tester");
        ReflectionTestUtils.setField(user2, "id", 2L);

        Post post = new Post(user, "테스트 제목", "테스트 내용");
        ReflectionTestUtils.setField(post, "id", 1L);
        ReflectionTestUtils.setField(post, "likeCount", 1);

        Like existingLike = new Like(user2, post);

        given(userService.getUser(authUser)).willReturn(user2);
        given(postService.postInfo(1L)).willReturn(post);
        given(likeRepository.findByUserAndPost(user2, post)).willReturn(Optional.of(existingLike));
        // when
        PostLikeResponse response = likeService.likePost(authUser, 1L);
        // then
        assertThat(response.isLiked()).isFalse();
        assertThat(response.getPostLikeCount()).isEqualTo(0);
        verify(likeRepository).delete(existingLike);
    }

    @Test
    void 댓글에_좋아요를_누를수_있다() {
        // given
        AuthUser authUser = new AuthUser(1L, "닉네임" , "test@email.com");
        User user = new User("닉네임", "test@email.com", "1234Test");
        ReflectionTestUtils.setField(user, "id", 1L);
        User user2 = new User("테스터", "tester@email.com", "1234Tester");
        ReflectionTestUtils.setField(user2, "id", 2L);

        Post post = new Post(user, "테스트 제목", "테스트 내용");
        ReflectionTestUtils.setField(post, "id", 1L);

        Comment comment = new Comment(user2, post, "테스트입니다");
        ReflectionTestUtils.setField(comment, "id", 1L);
        ReflectionTestUtils.setField(comment, "likeCount", 0);

        given(userService.getUser(authUser)).willReturn(user);
        given(postService.postInfo(1L)).willReturn(post);
        given(commentService.commentInfo(1L)).willReturn(comment);
        given(likeRepository.findByUserAndComment(user, comment)).willReturn(Optional.empty());
        //when
        CommentLikeResponse response = likeService.likeComment(authUser, 1L, 1L);
        // then
        assertThat(response.isLiked()).isTrue();
        assertThat(response.getCommentLikeCount()).isEqualTo(1);
        verify(likeRepository).save(any(Like.class));
    }

    @Test
    void 댓글_좋아요를_취소한다() {
        // given
        AuthUser authUser = new AuthUser(1L, "닉네임" , "test@email.com");
        User user = new User("닉네임", "test@email.com", "1234Test");
        ReflectionTestUtils.setField(user, "id", 1L);
        User user2 = new User("테스터", "tester@email.com", "1234Tester");
        ReflectionTestUtils.setField(user2, "id", 2L);

        Post post = new Post(user, "테스트 제목", "테스트 내용");
        ReflectionTestUtils.setField(post, "id", 1L);

        Comment comment = new Comment(user2, post, "테스트입니다");
        ReflectionTestUtils.setField(comment, "id", 1L);
        ReflectionTestUtils.setField(comment, "likeCount", 1);

        Like existingLike = new Like(user, post ,comment);

        given(userService.getUser(authUser)).willReturn(user);
        given(postService.postInfo(1L)).willReturn(post);
        given(commentService.commentInfo(1L)).willReturn(comment);
        given(likeRepository.findByUserAndComment(user, comment)).willReturn(Optional.of(existingLike));
        // when
        CommentLikeResponse response = likeService.likeComment(authUser, 1L, 1L);
        // then
        assertThat(response.isLiked()).isFalse();
        assertThat(response.getCommentLikeCount()).isEqualTo(0);
        verify(likeRepository).delete(existingLike);
    }
}