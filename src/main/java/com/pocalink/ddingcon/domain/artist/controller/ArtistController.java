package com.pocalink.ddingcon.domain.artist.controller;

import com.pocalink.ddingcon.domain.artist.dto.response.ArtistsResponseDto;
import com.pocalink.ddingcon.domain.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<ArtistsResponseDto> findArtists(@RequestParam(required = false) String keyword) {
        ArtistsResponseDto artistsResponseDto = artistService.findArtists(keyword);
        return ResponseEntity.ok(artistsResponseDto);
    }
}
