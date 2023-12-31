package com.pocalink.ddingcon.domain.auth.service;

import com.pocalink.ddingcon.global.security.AuthTokens;
import com.pocalink.ddingcon.global.security.AuthTokensGenerator;
import com.pocalink.ddingcon.domain.auth.dto.response.OAuthInfoResponse;
import com.pocalink.ddingcon.domain.member.domain.Member;
import com.pocalink.ddingcon.global.security.OAuthLoginParams;
import com.pocalink.ddingcon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByNickname(oAuthInfoResponse.findByNickname())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .nickname(oAuthInfoResponse.findByNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getId();
    }
}
