package com.example.login_test.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

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

    @Column(length = 16, nullable = true)
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

    public void output(){
        System.out.println(id);
        System.out.println(email);
        System.out.println(password);
        System.out.println(username);
        System.out.println(phoneNumber);
        System.out.println(roles);
    }
}
