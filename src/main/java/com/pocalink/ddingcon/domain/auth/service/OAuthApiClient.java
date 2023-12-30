package com.pocalink.ddingcon.domain.auth.service;

import com.pocalink.ddingcon.domain.auth.dto.response.OAuthInfoResponse;
import com.pocalink.ddingcon.domain.member.domain.OAuthLoginParams;
import com.pocalink.ddingcon.domain.member.domain.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
