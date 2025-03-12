package org.example.bagicnewspeed.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private final long id;
    private final String nickName;
    private final String email;

    public AuthUser(long id, String nickName, String email) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
    }
}
