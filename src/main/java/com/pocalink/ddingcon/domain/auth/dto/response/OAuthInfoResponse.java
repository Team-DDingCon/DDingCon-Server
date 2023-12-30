package com.pocalink.ddingcon.domain.auth.dto.response;

import com.pocalink.ddingcon.domain.member.domain.OAuthProvider;

public interface OAuthInfoResponse {
    String getNickname();
    OAuthProvider getOAuthProvider();
}
