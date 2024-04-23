package dev.nateschieber.groovesprings.joins;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "albumToTrack")
public record AlbumToTrack(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id,
    long albumId,
    long trackId,
    boolean archived) { }
