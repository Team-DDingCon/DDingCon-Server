package com.pocalink.ddingcon.domain.artist.dto.response;

import com.pocalink.ddingcon.domain.artist.domain.Artist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArtistResponseDto {

    private final Long id;
    private final String koName;
    private final String enName;

    public static ArtistResponseDto from(Artist artist) {
        return new ArtistResponseDto(artist.getId(), artist.getKoName(), artist.getEnName());
    }
}
