package com.pocalink.ddingcon.domain.auth.dto.response;

import com.pocalink.ddingcon.global.security.OAuthProvider;

public interface OAuthInfoResponse {
    OAuthProvider getOAuthProvider();

    String getNickname();
}
