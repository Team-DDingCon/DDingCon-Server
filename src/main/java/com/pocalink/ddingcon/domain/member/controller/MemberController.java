package com.pocalink.ddingcon.domain.member.controller;

import com.pocalink.ddingcon.domain.member.domain.Member;
import com.pocalink.ddingcon.domain.member.repository.MemberRepository;
import com.pocalink.ddingcon.global.error.ErrorResponse;
import com.pocalink.ddingcon.global.security.AuthTokensGenerator;
import com.pocalink.ddingcon.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final JwtTokenProvider JwtTokenProvider;

    @GetMapping
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<?> findByAccessToken(@RequestHeader("Authorization") String accessToken){
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        if(!JwtTokenProvider.validateToken(accessToken)) {
            return new ResponseEntity<>(ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), "만료된 토큰입니다."), HttpStatus.UNAUTHORIZED);
        }

        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            return new ResponseEntity<>(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), "조회된 회원이 없습니다."), HttpStatus.NOT_FOUND);
        }

    }
}
