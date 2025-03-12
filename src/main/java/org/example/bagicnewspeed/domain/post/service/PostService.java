package org.example.bagicnewspeed.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.example.bagicnewspeed.domain.auth.dto.AuthUser;
import org.example.bagicnewspeed.domain.post.dto.request.PostRequest;
import org.example.bagicnewspeed.domain.post.dto.response.PostResponse;
import org.example.bagicnewspeed.domain.post.entity.Post;
import org.example.bagicnewspeed.domain.post.repository.PostRepository;
import org.example.bagicnewspeed.domain.user.entity.User;
import org.example.bagicnewspeed.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponse createPost(AuthUser authUser, PostRequest postRequest) {
        User user = userService.getUser(authUser);
        Post post = new Post(
                user,
                postRequest.getTitle(),
                postRequest.getContent()
        );
        postRepository.save(post);
        return new PostResponse(
                post.getId(),
                post.getUser().getNickName(),
                post.getTitle(),
                post.getContent());
    }
}
