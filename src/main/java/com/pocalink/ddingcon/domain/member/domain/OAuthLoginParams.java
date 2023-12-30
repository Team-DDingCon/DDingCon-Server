package com.pocalink.ddingcon.domain.member.domain;

import com.pocalink.ddingcon.domain.member.domain.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
