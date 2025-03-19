package org.example.bagicnewspeed.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private String nickName;
    private String email;


    public UserResponse(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }
}
