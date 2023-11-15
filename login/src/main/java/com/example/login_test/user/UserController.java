package com.example.login_test.user;

import com.example.login_test.core.security.JwtTokenProvider;
import com.example.login_test.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        userService.join(requestDTO);

        return ResponseEntity.ok( ApiUtils.success(null) );
    }


    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {
        userService.checkEmail(requestDTO.getEmail());
        return ResponseEntity.ok( ApiUtils.success(null) );
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        String jwt = userService.login(requestDTO);

        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));
    }


    @PostMapping("/kakaoJoin")
    public ResponseEntity<?> kakaoJoin(@RequestBody UserRequest.KakaoJoinDTO kakaoUser, Error error) {

        userService.kakaoJoin(kakaoUser);

        userService.printKakaoUserInfo(kakaoUser.getEmail());

        return ResponseEntity.ok(ApiUtils.success(null));
    }


    @PostMapping("/kakaoLogin")
    public ResponseEntity<?> kakaoLogin(@RequestBody UserRequest.KakaoLoginDTO kakaoUser, Error error) {

        String jwt = userService.kakaoLogin(kakaoUser);

        //조인은 뜨고 로그인은 안뜸 이유 찾아서 해결하기
        // html 바로 받아오는게 맞는 지 확인
        //userService.printKakaoUserInfo(kakaoUser.getEmail());

        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));
    }

}
