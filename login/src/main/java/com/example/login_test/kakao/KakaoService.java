package com.example.login_test.kakao;

import com.example.login_test.user.User;
import com.example.login_test.user.UserRepository;
import com.example.login_test.user.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;


@Service
public class KakaoService {

    @Autowired
    private UserRepository userRepository;

    @Value("${kakao.api.key}")
    private String API_KEY;

    @Value("${kakao.api.authUri}")
    private String KAKAO_AUTH_URI;

    @Value("${kakao.api.redirectUri}")
    private String REDIRECT_URI;

    @Value("${kakao.api.secret}")
    private String SECRET_KEY;


    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/authorize"
                + "?client_id=" + API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";
    }


    // 코드를 가져 와서 카카오 토큰을 가져오는 메서드
    public String getKakaoToken(String code, HttpSession session) {
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Failed to get authorization code");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", API_KEY);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("client_secret", SECRET_KEY);
        params.add("code", code);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(KAKAO_AUTH_URI + "/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), "Failed to get access token");
        }

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);
            KakaoApiResponse kakaoApiResponse = gson.fromJson(responseEntity.getBody(), KakaoApiResponse.class);

            String refresh_token = kakaoApiResponse.getRefresh_token();
            String access_token = kakaoApiResponse.getAccess_token();
            int expires_in = kakaoApiResponse.getExpires_in();
            int refresh_token_expires_in = kakaoApiResponse.getRefresh_token_expires_in();

            // session에 토큰 넣어두기
            session.setAttribute("access_token", access_token);

            System.out.println("getKakaoToken");
            System.out.println("access_token: " + access_token);
            System.out.println("expires_in: " + expires_in);
            System.out.println("refresh_token:" + refresh_token);
            System.out.println("refresh_token_expires_in:" + refresh_token_expires_in);

            return jsonObject.get("access_token").getAsString();
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get access token");
        }
    }


    //액세스 토큰으로 카카오 API를 통해 사용자 정보를 가져옴
    public KakaoUserInforDto getKakaoInfo(String accessToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    entity,
                    String.class);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), "Failed to get user info");
        }

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            Gson gson = new Gson();
            KakaoUserInforResponse kakaoUserInforResponse = gson.fromJson(responseEntity.getBody(), KakaoUserInforResponse.class);

            String nickname = kakaoUserInforResponse.getProperties().getNickname();
            String email = kakaoUserInforResponse.getKakao_account().getEmail();

            System.out.println("getKakaoInfo");
            System.out.println(nickname);
            System.out.println(email);

            //가져온 정보를 KakaoUserInforDto 객체에 담아 반환a
            return new KakaoUserInforDto(nickname, email);

        } else {
            System.out.println("Failed to get access token. Response: " + responseEntity.getBody());
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to get user info");
        }
    }


    public void join(HttpSession session) throws JsonProcessingException {
        // sessioin에 넣었던 토큰 가져와서 사용
        KakaoUserInforDto kakaoUserInfor = getKakaoInfo((String) session.getAttribute("access_token"));

        String nickname = kakaoUserInfor.getNickname();
        String email = kakaoUserInfor.getEmail();

        // UserDTO 객체 생성
        UserRequest.KakaoJoinDTO kakaoJoinDTO = new UserRequest.KakaoJoinDTO();
        kakaoJoinDTO.setUsername(nickname);
        kakaoJoinDTO.setEmail(email);

        // UserRepository를 사용하여 User 저장
        userRepository.save(kakaoJoinDTO.toEntity());
    }


}
