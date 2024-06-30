import {Artist} from "./artist.model";

export interface ArtistsByAlbumIds {
  artists: Artist[];
  count: number;
  albumIds: number[];
}
