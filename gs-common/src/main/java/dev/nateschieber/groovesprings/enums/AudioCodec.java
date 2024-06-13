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
  MP3_VAR("mp3", 200);

  private String fileExtension;
  private int priceFactor;

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

}
