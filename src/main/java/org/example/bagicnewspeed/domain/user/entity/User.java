package org.example.bagicnewspeed.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bagicnewspeed.common.base.BaseEntity;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    public User(String nickName,String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String newNickName, String newEmail) {
        this.nickName = newNickName;
        this.email = newEmail;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
