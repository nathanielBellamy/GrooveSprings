package dev.nateschieber.groovesprings.enums;

public enum AudioCodec {
  WAV("wav", 1000),
  ALAC("alac", 900),
  AAC("aac", 800),
  FLAC("flac", 700),
  OGG("ogg", 600),
  MP3_320("mp3", 500),
  MP3_256("mp3", 400),
  MP3_192("mp3", 300),
  MP3_VAR("mp3", 200),
  UNRECOGNIZED("xxx", 100);

  private final String fileExtension;
  private final int priceFactor;

  AudioCodec(String fileExtension, int priceFactor) {
    this.fileExtension = fileExtension;
    this.priceFactor = priceFactor;
  }

  public int getPriceFactor() {
    return priceFactor;
  }

  public String getFileExtension() {
    return fileExtension;
  }

  public static AudioCodec fromStringAndBitRate(String str, Long bitRate, Boolean isVariableBitrate) {
    // TODO: do rest of codecs, right now we only have FLAC + MP3 for testing
    if (str.equals("FLAC")) {
      return AudioCodec.FLAC;
    } else if (str.equals("MP3")) {
      if (isVariableBitrate) {
        return AudioCodec.MP3_VAR;
      } else {
        if (bitRate == 320) {
          return AudioCodec.MP3_320;
        } else if (bitRate == 256) {
          return AudioCodec.MP3_256;
        } else {
          return AudioCodec.MP3_192;
        }
      }
    } else {
      return AudioCodec.UNRECOGNIZED;
    }
  }
}
