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

  public static void initPlaybackLoop(Long threadId, String filePath, Long initialFrameId, Long vst3HostAppPtr) {
    long initFrameId = initialFrameId == null ? 0l : initialFrameId.longValue();
    long vst3Ptr = vst3HostAppPtr == null ? 0l : initialFrameId.longValue();
    new JniMain().initPlaybackLoopNative(threadId, filePath, initFrameId, vst3Ptr);
  }

  private native void initPlaybackLoopNative(long threadId, String filePath, long initialFrameId, long vst3HostAppPtr); // blocking

  public static SfInfo readSfInfo(String filePath) {
    SfInfo res = new JniMain().readSfInfoNative(filePath);
    return res;
  }

  private native SfInfo readSfInfoNative(String filePath);

  public static long allocVst3Host() {
    return new JniMain().allocVst3HostNative();
  };

  private native long allocVst3HostNative();

  public static long deleteVst3Host(long vst3HostAppPtr) {
    return new JniMain().deleteVst3HostNative(vst3HostAppPtr);
  };

  private native long deleteVst3HostNative(long vst3HostAppPtr);

  public static void initVst3Host(long appPtr) {
    new JniMain().initVst3HostNative(appPtr);
  }

  private native void initVst3HostNative(long appPtr); // blocking
}
