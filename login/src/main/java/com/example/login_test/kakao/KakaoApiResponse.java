package com.example.login_test.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoApiResponse {
    private final String access_token;
    private final String token_type;
    private final String refresh_token;
    private final int expires_in;
    private final int refresh_token_expires_in;
    private final String scope;
}