package com.example.login_test.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class KakaoUserInforResponse {
    private final long id;
    private final Properties properties;
    private final KakaoAccount kakao_account;

    @Getter
    @AllArgsConstructor
    public static class Properties {
        private final String nickname;
        //private final String profile_image;
    }

    @Getter
    @AllArgsConstructor
    public static class KakaoAccount {
        private final String email;
    }
}
