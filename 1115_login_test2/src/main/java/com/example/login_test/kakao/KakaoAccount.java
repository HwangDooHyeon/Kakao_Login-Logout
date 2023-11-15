package com.example.login_test.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class KakaoAccount {
    private final String email;
    private final String username; // 'Profile' 클래스를 제거하고 'username' 필드를 추가

    // 'Profile' 클래스 제거
}
