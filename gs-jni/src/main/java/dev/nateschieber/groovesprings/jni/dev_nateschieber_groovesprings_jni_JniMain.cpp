#include "jni.h"
#include <chrono>
#include <iostream>
#include <string>
#include <thread>
#include "dev_nateschieber_groovesprings_jni_JniMain.h"

// NOTE:
//   - on macOs, .dylib compilation requires main method
int main() {
    return 0;
}

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_helloWorld
  (JNIEnv* env, jobject thisObject) {
    std::cout << "Hey, Java! Tell Scala that C++ says hi !!" << std::endl;
}

JNIEXPORT jint JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_addNative
  (JNIEnv* env, jobject thisObject, jint x, jint y) {
  return x + y;
}

void playbackTask(JNIEnv* env, jobject gsPlayback, jmethodID setCurrFrameId, int currFrameId)
{
    if (currFrameId % 10 == 0)
    {
        currFrameId += 1;
        std::cout << "cpp-currFrameId: " << currFrameId;
        try {
//            env->CallVoidMethod(gsPlayback, setCurrFrameId, currFrameId);
        } catch (...) {
            std::cout << "cpp- UH OH!";
        }
    }
}

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initPlaybackLoopNative
  (JNIEnv* env, jobject _gsPlayback) {

  std::cout << "\n initPlaybackLoopNative \n";

  try {

      int currFrameId;
      currFrameId = 0;

      std::cout << "Hello World! " << currFrameId;
      jclass gsPlayback = env->FindClass("dev/nateschieber/groovesprings/actors/GsPlayback");
      jmethodID setCurrFrameId = env->GetMethodID (gsPlayback, "setCurrFrameId", "java.lang.Integer");

      std::thread playbackThread(playbackTask, env, gsPlayback, setCurrFrameId, currFrameId);

      std::cout << "\n Hey buddy \n";


      std::this_thread::sleep_for(std::chrono::milliseconds(100000));

      playbackThread.join();
   }
   catch(std::exception const& e)
   {
      std::cout << "Message: " << e.what() << "\n";
   }
}
