package dev.nateschieber.groovesprings.jni;

public class JniMain {

  static {
    System.out.println(System.getProperties());
    System.loadLibrary("native");
  }

  public static void main(String[] args) {
    new JniMain().helloWorld();
  }

  public static int add(int x, int y) {
    return new JniMain().addNative(x, y);
  }

  private native void helloWorld();

  private native int addNative(int x, int y);

  public static void initPlaybackLoop() { new JniMain().initPlaybackLoopNative(); }

  private native void initPlaybackLoopNative();
}
