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

    /* @Valid = 받아온 폼의 데이터 유효성을 검사하는 역할을 수행.
     *  - @RequestBody, @ModelAttribute 와 함께 사용한다.
     *  - DTO에서 작성된  @Size, @Pattern, @NotEmpty 등등을 검사.
     *  - 필드에 'NOT NULL' 조건이 있거나, 'UNIQUE' 조건이 설정되어 있는 경우도 확인.
     *
     * @RequestBody
     * JSON 으로 넘어오는 데이터를 UserRequest.LoginDTO 형태로 변경 해주는 역할.
     */
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
}
























