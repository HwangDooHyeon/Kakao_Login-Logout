package com.example.login_test.kakao;

import lombok.Getter;

// 서비스 클래스에서 만들어진 카카오 인포 값이 저장 된 곳.
// 유지보수, 수정용이 등등 이점을 위해서 (제이슨 객체의 값을 서비스클래스에서 바로 리턴값을 출력하지 않도록 하는 이유)
@Getter
public class KakaoUserInforDto {

    private String nickname;
    private String email;
    //private final String profileImage;

    public KakaoUserInforDto(String nickname, String email) {
            this.nickname = nickname;
            this.email = email;
            //this.profileImage = profileImage;
    }
}

