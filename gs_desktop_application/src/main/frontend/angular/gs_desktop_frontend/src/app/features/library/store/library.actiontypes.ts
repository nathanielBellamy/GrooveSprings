import {SetPlaylistsFilter} from "./library.actions";

export enum LibraryActionTypes {
  // identity
  Identity = '[Library] Identity',

  // Library Scan
  LibraryScan = '[Library] Run Scan',
  LibraryScanSuccess = '[Library] Scan Success',
  LibraryScanFailure = '[Library] Scan Failure',

  // Clear Library
  ClearLibrary = '[Library] Clear Library',
  ClearLibrarySuccess = '[Library] Clear Library Success',
  ClearLibraryFailure = '[Library] Clear Library Failure',


  // fetch actions
  FetchAll = '[Library] Fetch All',

  // fetch
  FetchAlbums = '[Albums] Fetch Albums',
  FetchAlbumsSuccess = '[Albums] Fetch Albums Success',
  FetchAlbumsFailure = '[Albums] Fetch Albums Failure',

  FetchArtists = '[Artists] Fetch Artists',
  FetchArtistsSuccess = '[Artists] Fetch Artists Success',
  FetchArtistsFailure = '[Artists] Fetch Artists Failure',

  FetchTracks = '[Tracks] Fetch Tracks',
  FetchTracksSuccess = '[Tracks] Fetch Tracks Success',
  FetchTracksFailure = '[Tracks] Fetch Tracks Failure',

  FetchPlaylists = '[Playlists] Fetch Playlists',
  FetchPlaylistsSuccess = '[Playlists] Fetch Playlists Success',
  FetchPlaylistsFailure = '[Playlists] Fetch Playlists Failure',

  // filter actions
  SetArtistsFilter = '[Filters] Set Artists Filter',
  SetArtistsFilterAlbumsSuccess = '[Filters] Set Artists Filter Albums Success',
  SetArtistsFilterAlbumsFailure = '[Filters] Set Artists Filter Albums Failure',
  SetArtistsFilterTracksSuccess = '[Filters] Set Artists Filter Tracks Success',
  SetArtistsFilterTracksFailure = '[Filters] Set Artists Filter Tracks Failure',
  SetArtistsFilterPlaylistsSuccess = '[Filters] Set Artists Filter Playlists Success',
  SetArtistsFilterPlaylistsFailure = '[Filters] Set Artists Filter Playlists Failure',
  ClearArtistsFilter = '[Filters] Clear Artists Filter',

  SetAlbumsFilter = '[Filters] Set Albums Filter',
  SetAlbumsFilterArtistsSuccess = '[Filters] Set Albums Filter Artists Success',
  SetAlbumsFilterArtistsFailure = '[Filters] Set Albums Filter Artists Failure',
  SetAlbumsFilterTracksSuccess = '[Filters] Set Albums Filter Tracks Success',
  SetAlbumsFilterTracksFailure = '[Filters] Set Albums Filter Tracks Failure',
  SetAlbumsFilterPlaylistsSuccess = '[Filters] Set Albums Filter Playlists Success',
  SetAlbumsFilterPlaylistsFailure = '[Filters] Set Albums Filter Playlists Failure',
  ClearAlbumsFilter = '[Filters] Clear Albums Filter',

  SetPlaylistsFilter = '[Filters] Set Playlists Filter',
  SetPlaylistsFilterAlbumsSuccess = '[Filters] Set Playlists Filter Albums Success',
  SetPlaylistsFilterAlbumsFailure = '[Filters] Set Playlists Filter Albums Failure',
  SetPlaylistsFilterArtistsSuccess = '[Filters] Set Playlists Filter Artists Success',
  SetPlaylistsFilterArtistsFailure = '[Filters] Set Playlists Filter Artists Failure',
  SetPlaylistsFilterTracksSuccess = '[Filters] Set Playlists Filter Tracks Success',
  SetPlaylistsFilterTracksFailure = '[Filters] Set Playlists Filter Tracks Failure',
  ClearPlaylistsFilter = '[Filters] Clear Playlists Filter'
}
