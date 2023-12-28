package com.pocalink.ddingcon.domain.artist.service;

import com.pocalink.ddingcon.domain.artist.domain.Artist;
import com.pocalink.ddingcon.domain.artist.dto.response.ArtistsResponseDto;
import com.pocalink.ddingcon.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistsResponseDto findArtists(String keyword) {
        List<Artist> artists = artistRepository.findByKoNameContainingOrEnNameContaining(keyword, keyword);
        return ArtistsResponseDto.from(artists);
    }
}
