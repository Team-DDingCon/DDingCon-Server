package com.pocalink.ddingcon.domain.auth.service;

import com.pocalink.ddingcon.domain.auth.dto.response.OAuthInfoResponse;
import com.pocalink.ddingcon.global.security.OAuthLoginParams;
import com.pocalink.ddingcon.global.security.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
