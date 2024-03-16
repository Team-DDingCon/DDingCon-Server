package com.pocalink.ddingcon.domain.auth.service;

import com.pocalink.ddingcon.domain.auth.dto.response.KakaoInfoResponse;
import com.pocalink.ddingcon.domain.auth.dto.response.KakaoTokens;
import com.pocalink.ddingcon.domain.auth.dto.response.OAuthInfoResponse;
import com.pocalink.ddingcon.global.security.OAuthLoginParams;
import com.pocalink.ddingcon.global.security.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        String url = authUrl + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        try{
            KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);

            assert response != null;
            return response.getAccessToken();
        }catch(RestClientException e){
            throw new RuntimeException("서버와의 통신 중 문제가 발생했습니다.");
        }catch (NullPointerException e){
            throw new RuntimeException("응답이 올바르지 않습니다.");
        }

    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";
        OAuthInfoResponse oauthInfoResponse = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        try {
            oauthInfoResponse = restTemplate.postForObject(url, request, KakaoInfoResponse.class);
            assert oauthInfoResponse != null;
        } catch(RestClientException e) {
            System.err.println("서버와의 통신 중 문제가 발생했습니다.");
            e.printStackTrace();
        } catch(NullPointerException e) {
            System.err.println("응답이 올바르지 않습니다.");
            e.printStackTrace();
        }

        return oauthInfoResponse;
    }
}
