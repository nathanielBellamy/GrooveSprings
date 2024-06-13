
export enum LibraryActionTypes {
  // identity
  Identity = '[Library] Identity',

  // fetch actions
  FetchAll = '[Library] Fetch All',

  // Library Scan
  RunLibraryScan = '[Library] Run Scan',
  LibraryScanComplete = '[Library] Scan Complete',
  LibraryScanFailure = '[Library] Scan Failure',

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

  // filter actions
  SetArtistsFilter = '[Filters] Set Artists Filter',
  SetArtistsFilterAlbumsSuccess = '[Filters] Set Artists Filter Albums Success',
  SetArtistsFilterAlbumsFailure = '[Filters] Set Artists Filter Albums Failure',
  SetArtistsFilterTracksSuccess = '[Filters] Set Artists Filter Tracks Success',
  SetArtistsFilterTracksFailure = '[Filters] Set Artists Filter Tracks Failure',
  ClearArtistsFilter = '[Filters] Clear Artists Filter',

  SetAlbumsFilter = '[Filters] Set Albums Filter',
  SetAlbumsFilterArtistsSuccess = '[Filters] Set Albums Filter Artists Success',
  SetAlbumsFilterArtistsFailure = '[Filters] Set Albums Filter Artists Failure',
  SetAlbumsFilterTracksSuccess = '[Filters] Set Albums Filter Tracks Success',
  SetAlbumsFilterTracksFailure = '[Filters] Set Albums Filter Tracks Failure',

  ClearAlbumsFilter = '[Filters] Clear Albums Filter'
}
