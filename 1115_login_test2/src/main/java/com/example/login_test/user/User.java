package com.example.login_test.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User{

    // ** 해당 필드를 PK로 지정
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = true)
    private String password;

    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 11, nullable = true)
    private String phoneNumber;

    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private Boolean isKakaoUser;

    // * 주로 생성자의 인자가 많거나, 인자를 선택적으로 지정 해야 하는 경우에 사용.
    @Builder
    public User(int id, String email, String password, String username, String phoneNumber, List<String> roles, Boolean isKakaoUser) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.isKakaoUser = isKakaoUser;
    }
}
