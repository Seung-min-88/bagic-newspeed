package org.example.bagicnewspeed.domain.post.service;

import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.post.dto.request.PostRequest;
import org.example.bagicnewspeed.domain.post.dto.response.PostResponse;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.post.repository.PostRepository;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.repository.UserRepository;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private PostService postService;

    @Test
    void 게시물을_저장할_수_있다() {
        // given
        AuthUser authUser = new AuthUser(1L,"닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com", "encodedPassword");
        PostRequest postRequest = new PostRequest();
        ReflectionTestUtils.setField(postRequest, "title", "테스트 제목");
        ReflectionTestUtils.setField(postRequest,"content", "테스트입니다");
        Post post = new Post(
                user,
                postRequest.getTitle(),
                postRequest.getContent()
        );
        given(userService.getUser(authUser)).willReturn(user);
        given(postRepository.save(any(Post.class))).willReturn(post);
        // when
        PostResponse savePost = postService.createPost(authUser, postRequest);
        // then
        assertNotNull(savePost);
        assertThat(post.getId()).isEqualTo(user.getId());
        assertThat(post.getUser().getNickName()).isEqualTo(user.getNickName());
        assertThat(post.getTitle()).isEqualTo(postRequest.getTitle());
        assertThat(post.getContent()).isEqualTo(postRequest.getContent());
    }

    @Test
    void 게시물을_수정할_수_있다() {
        // given
        AuthUser authUser = new AuthUser(1L,"닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com", "encodedPassword");
        Post post = new Post(user, "테스트 제목", "테스트입니다");

        PostRequest postRequest = new PostRequest();
        ReflectionTestUtils.setField(postRequest, "title", "수정된 제목");
        ReflectionTestUtils.setField(postRequest, "content", "수정된 내용");

        given(userService.getUser(authUser)).willReturn(user);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        // when
        PostResponse update = postService.updatePost(authUser, 1L , postRequest);
        //then
        assertNotNull(update);
        assertThat(post.getTitle()).isEqualTo(update.getTitle());
        assertThat(post.getContent()).isEqualTo(update.getContent());
    }

    @Test
    void 게시물을_삭제할_수_있다() {
        // given
        AuthUser authUser = new AuthUser(1L,"닉네임", "test@email.com");
        User user = new User("닉네임", "test@email.com", "encodedPassword");
        Post post = new Post(user, "테스트 제목", "테스트입니다");

        given(userService.getUser(authUser)).willReturn(user);
        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        // when
        postService.deletePost(authUser, 1L);
        // then
        assertThat(postRepository.findById(66L)).isEmpty();
    }
}