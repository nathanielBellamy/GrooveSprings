package dev.nateschieber.groovesprings.jni;

public class JniMain {

  static {
    System.out.println(System.getProperties());
    System.loadLibrary("native");
  }

  public static void main() {
    new JniMain().helloWorld();
  }

  public static int add(int x, int y) {
    return new JniMain().addNative(x, y);
  }

  private native void helloWorld();

  private native int addNative(int x, int y);
}
