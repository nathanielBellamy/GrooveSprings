package dev.nateschieber.groovesprings.jni;

public class JniMain {

  static {
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

  public static void initPlaybackLoop(Long threadId, String filePath, Long initialFrameId) {
    long initFrameId = initialFrameId == null ? 0l : initialFrameId.longValue();
    new JniMain().initPlaybackLoopNative(threadId, filePath, initialFrameId);
  }

  private native void initPlaybackLoopNative(long threadId, String filePath, long initialFrameId);

  public static SfInfo readSfInfo(String filePath) {
    SfInfo res = new JniMain().readSfInfoNative(filePath);
    return res;
  }

  private native SfInfo readSfInfoNative(String filePath);

  public static void initVst3Host() {
    System.out.println("initVst3HostStart");
    var vst3HostApp = new JniMain().initVst3HostNative();
    System.out.println("vst3HostApp addr: " + vst3HostApp);
    System.out.println("initVst3HostEnd");
  };

  private native long[] initVst3HostNative();

}
