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

  public static void initVst3Host(Vst3AudioHostAppPtr appAddr) {
    System.out.println("initVst3HostStart");
    Object vst3HostAppObj = new JniMain().initVst3HostNative(appAddr);

    System.out.println("\n HERERERERE HERER ERHERHERERE RERE RERERRE RERERERER ERERE");
    System.out.println("vst3HostApp addr: " + ((Vst3AudioHostAppPtr) vst3HostAppObj).getAddress());

//    List<Vst3AudioHostAppPtr> vst3AudioHostAppPtrs = new ArrayList<Vst3AudioHostAppPtr>();
//    Arrays.stream(vst3HostAppObjs).forEach(obj -> vst3AudioHostAppPtrs.add((Vst3AudioHostAppPtr) obj));

//    System.out.println("vst3HostAppPtrs ptrs: " + vst3AudioHostAppPtrs);

    System.out.println("initVst3HostEnd");
  };

  private native Object initVst3HostNative(Object appAddr);

}
