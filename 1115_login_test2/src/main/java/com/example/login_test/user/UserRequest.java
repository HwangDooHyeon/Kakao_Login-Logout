package com.example.login_test.user;


import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {

        // 데이터가 비어있을 수 없는 상태.
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        @NotEmpty
        @Size(min = 8, max = 20, message = "8자 이상 20자 이내로 작성 가능합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;

        @NotEmpty
        private String username;

        @NotEmpty
        @Pattern(regexp = "^[0-9]{10,11}$", message = "휴대폰 번호는 숫자 10~11자리만 가능합니다.")
        private String phoneNumber;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .phoneNumber(phoneNumber)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }
    @Getter
    @Setter
    public static class KakaoJoinDTO {
        private String email;
        private String username;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .username(username)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .isKakaoUser(true)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class KakaoLoginDTO {
        private String email;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .isKakaoUser(true)
                    .build();
        }
    }
}