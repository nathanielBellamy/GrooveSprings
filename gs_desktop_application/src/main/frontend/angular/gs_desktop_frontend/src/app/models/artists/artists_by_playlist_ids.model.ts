import {Artist} from "./artist.model";

export interface ArtistsByPlaylistIds {
  artists: Artist[];
  count: number;
  playlistIds: number[];
}
