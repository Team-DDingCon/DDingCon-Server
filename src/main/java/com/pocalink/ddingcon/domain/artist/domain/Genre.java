package com.pocalink.ddingcon.domain.artist.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {

    KPOP("케이팝"),
    POP("해외팝"),
    HIPHOP("힙합"),
    JAZZ("재즈"),
    INDIE("인디음악");

    private final String value;
}
