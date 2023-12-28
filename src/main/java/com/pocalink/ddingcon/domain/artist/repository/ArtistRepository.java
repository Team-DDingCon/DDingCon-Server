package com.pocalink.ddingcon.domain.artist.repository;

import com.pocalink.ddingcon.domain.artist.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByKoNameContainingOrEnNameContaining(String koKeyword, String enKeyword);
}
