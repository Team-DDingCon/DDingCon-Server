package com.pocalink.ddingcon.domain.artist.domain;

import com.pocalink.ddingcon.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Artist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String koName;

    @Column(nullable = false)
    private String enName;

    @Column(length = 2084)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
