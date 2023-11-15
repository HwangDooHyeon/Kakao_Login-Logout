package com.example.login_test.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoTokenService {

    @Value("${kakao.api.key}")
    private String API_KEY;

    @Value("${kakao.api.secret}")
    private String SECRET_KEY;

    public boolean isAccessTokenValid(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = "https://kapi.kakao.com/v1/user/access_token_info";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        System.out.println(accessToken);

        try {
            restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return false;
            } else {
                throw e;
            }
        }
    }


    public KakaoApiResponse refreshToken(String refreshToken) {

        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "refresh_token");
        parameters.add("client_id", API_KEY);
        parameters.add("client_secret", SECRET_KEY);
        parameters.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, null);

        ResponseEntity<KakaoApiResponse> responseEntity = restTemplate.postForEntity(requestUrl, requestEntity, KakaoApiResponse.class);

        System.out.println(refreshToken);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode(), "Failed to refresh token");
        }
    }

}
