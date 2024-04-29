package dev.nateschieber.groovesprings.audio.jni;

public class JniMain {

  static {
    System.loadLibrary("native");
  }

  public static void main() {
    new JniMain().helloWorld();
  }

  private native void helloWorld();
}