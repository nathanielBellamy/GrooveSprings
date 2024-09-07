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

  private native void initPlaybackLoopNative(long threadId, String filePath, long initialFrameId); // blocking

  public static SfInfo readSfInfo(String filePath) {
    SfInfo res = new JniMain().readSfInfoNative(filePath);
    return res;
  }

  private native SfInfo readSfInfoNative(String filePath);

  public static Vst3AudioHostAppPtr allocVst3Host(Vst3AudioHostAppPtr ptr) {
    return (Vst3AudioHostAppPtr) new JniMain().allocVst3HostNative((Object) ptr);
  };

  private native Object allocVst3HostNative(Object ptr);

  public static void initVst3Host(Vst3AudioHostAppPtr ptr) {
    new JniMain().initVst3HostNative(ptr.getAddress());
  }

  private native void initVst3HostNative(int addr); // blocking
}
