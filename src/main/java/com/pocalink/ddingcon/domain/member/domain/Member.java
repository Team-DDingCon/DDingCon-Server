package com.pocalink.ddingcon.domain.member.domain;

import com.pocalink.ddingcon.global.security.OAuthProvider;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private OAuthProvider oAuthProvider;

    @Builder
    public Member(String email, String nickname, OAuthProvider oAuthProvider) {
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
    }
}