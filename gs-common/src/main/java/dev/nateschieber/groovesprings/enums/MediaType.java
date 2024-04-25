package dev.nateschieber.groovesprings.enums;

public enum MediaType {
  WAV(1000),
  ALAC(900),
  AAC(800),
  FLAC(700),
  OGG(600),
  MP3_320(500),
  MP3_256(400),
  MP3_192(300),
  MP3_VAR(200);

  private int priceFactor;

  MediaType(int priceFactor) {
    this.priceFactor = priceFactor;
  }

  public int getPriceFactor() {
    return priceFactor;
  }

  public void setPriceFactor(int priceFactor) {
    this.priceFactor = priceFactor;
  }
}
