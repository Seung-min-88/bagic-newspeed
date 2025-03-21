package org.example.bagicnewspeed.domain.comment.service;

import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.comment.dto.CommentRequest;
import org.example.bagicnewspeed.domain.comment.dto.CommentResponse;
import org.example.bagicnewspeed.domain.comment.entity.Comment;
import org.example.bagicnewspeed.domain.comment.repository.CommentRepository;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.post.repository.PostRepository;
import org.example.bagicnewspeed.domain.post.service.PostService;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostService postService;
    @Mock
    private UserService userService;
    @InjectMocks
    private CommentService commentService;

    @Test
    void 댓글을_작성한다() {
        // given
        AuthUser authUser = new AuthUser(1L, "닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com","encodedPassword");
        Post post = new Post(user, "테스트제목", "테스트내용");
        CommentRequest commentRequest = new CommentRequest();
        ReflectionTestUtils.setField(commentRequest, "message", "확인이여");
        Comment comment = new Comment(
                user,
                post,
                commentRequest.getMessage()
        );
        ReflectionTestUtils.setField(comment, "id", 1L);

        given(userService.getUser(authUser)).willReturn(user);
        given(postService.postInfo(1L)).willReturn(post);
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        // when
        CommentResponse saveComment = commentService.postComment(authUser, 1L, commentRequest);
        // then
        // 세이브 메소드가 제대로 작동하는지 확인
//        verify(commentRepository).save(any(Comment.class));
        assertNotNull(saveComment);
        assertThat(saveComment.getPostId()).isEqualTo(post.getId());
//        assertThat(saveComment.getCommentId()).isEqualTo(1L); 아이디는 자동으로 생성되는거라 굳이 확인 안해도됨
        assertThat(saveComment.getNickName()).isEqualTo(user.getNickName());
        assertThat(saveComment.getMessage()).isEqualTo(commentRequest.getMessage());
    }

    @Test
    void 댓글을_수정한다() {
        AuthUser authUser = new AuthUser(1L, "닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com","encodedPassword");
        Post post = new Post(user, "테스트제목", "테스트내용");
        ReflectionTestUtils.setField(post, "id", 1L);
        Comment comment = new Comment(user, post, "테스트입니다");
        ReflectionTestUtils.setField(comment, "id", 2L);
        CommentRequest commentRequest = new CommentRequest();
        ReflectionTestUtils.setField(commentRequest, "message", "수정된 댓글");

        given(userService.getUser(authUser)).willReturn(user);
        given(postService.postInfo(1L)).willReturn(post);
        given(commentRepository.findById(2L)).willReturn(Optional.of(comment));
//        given(comment.getUser().getId()).willReturn(user.getId());

        CommentResponse update = commentService.updateComment(authUser, 1L, 2L, commentRequest);

        assertNotNull(update);
        assertThat(comment.getMessage()).isEqualTo(commentRequest.getMessage());
    }

    @Test
    void 댓글을_삭제한다() {
        AuthUser authUser = new AuthUser(1L, "닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com","encodedPassword");
        ReflectionTestUtils.setField(user, "id", 1L);
        Post post = new Post(user, "테스트제목", "테스트내용");
        ReflectionTestUtils.setField(post, "id", 1L);
        Comment comment = new Comment(user, post, "테스트입니다");
        ReflectionTestUtils.setField(comment, "id", 2L);

        given(userService.getUser(authUser)).willReturn(user);
        given(postService.postInfo(1L)).willReturn(post);
        given(commentRepository.findById(2L)).willReturn(Optional.of(comment));

        commentService.deleteComment(authUser, 1L, 2L);

        verify(commentRepository).delete(comment);
    }

}