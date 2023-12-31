package com.pocalink.ddingcon.domain.member.controller;

import com.pocalink.ddingcon.domain.member.domain.Member;
import com.pocalink.ddingcon.domain.member.repository.MemberRepository;
import com.pocalink.ddingcon.global.error.ErrorResponse;
import com.pocalink.ddingcon.global.security.AuthTokensGenerator;
import com.pocalink.ddingcon.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{accessToken}")
    public ResponseEntity<?> findByAccessToken(@PathVariable String accessToken) {
        if(!JwtTokenProvider.validateToken(accessToken)) {
            return new ResponseEntity<>(ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), "AccessToken is expired or invalid"), HttpStatus.UNAUTHORIZED);
        }

        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            return new ResponseEntity<>(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), "Member not found"), HttpStatus.NOT_FOUND);
        }
    }
}
