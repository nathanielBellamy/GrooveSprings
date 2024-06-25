package dev.nateschieber.groovesprings.rest.dtos.playlist;

import java.util.List;

public record PlaylistGetByAlbumIdsDto(
  List<Long> albumIds
) implements PlaylistDto { }
