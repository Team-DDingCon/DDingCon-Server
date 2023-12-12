package com.pocalink.ddingcon.domain.artist.dto.response;

import com.pocalink.ddingcon.domain.artist.domain.Artist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ArtistsResponseDto {

    private final List<ArtistResponseDto> artists;

    public static ArtistsResponseDto from(List<Artist> artists) {
        return new ArtistsResponseDto(
                artists.stream()
                        .map(ArtistResponseDto::from)
                        .toList());
    }
}
