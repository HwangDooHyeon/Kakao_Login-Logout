package com.example.login_test.kakao;

import com.example.login_test.user.UserRequest;
import com.example.login_test.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class KakaoController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping("/kakao_login")
    public String login(@RequestParam(value = "code", required = false) String code, HttpSession session) throws JsonProcessingException {
        if (code == null) {
            return "redirect:" + kakaoService.getKakaoLogin();
        }
        try {
            String accessToken = kakaoService.getKakaoToken(code);
            KakaoAccount kakaoAccount = kakaoService.getKakaoInfo(accessToken);

            // 세션에 액세스 토큰과 사용자 정보 저장
            session.setAttribute("accessToken", accessToken);
            session.setAttribute("userInformation", kakaoAccount);
            return "redirect:/choseJoin.html";

        } catch (Exception e) {
            return "error_page";
        }
    }

    @PostMapping("/kakaoJoin")
    public String kakaoJoin(HttpSession session) {
        // 세션에서 사용자 정보 받아오기
        KakaoAccount userInformation = (KakaoAccount) session.getAttribute("userInformation");

        if (userInformation == null) {
            throw new IllegalArgumentException("세션에 사용자 정보가 존재하지 않습니다.");
        }

        // KakaoJoinDTO 객체 생성 및 필드 설정
        UserRequest.KakaoJoinDTO kakaoUser = new UserRequest.KakaoJoinDTO();
        kakaoUser.setEmail(userInformation.getEmail());
        kakaoUser.setUsername(userInformation.getUsername());  // 'username' 필드 설정

        userService.kakaoJoin(kakaoUser);

        return "redirect:/login";
    }
}
