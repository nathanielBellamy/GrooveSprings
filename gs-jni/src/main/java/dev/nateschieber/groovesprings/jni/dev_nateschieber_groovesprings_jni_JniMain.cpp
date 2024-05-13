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
//    if (currFrameId % 10 == 0)
//    {
        currFrameId += 1;
        jint jCurrFrameId = static_cast<jint>(currFrameId);
        std::cout << "\n cpp-currFrameId: " << currFrameId;
        std::cout << "\n cpp-env JNIEnv: " << env;
        std::cout << "\n cpp-gsPlayback jobject: " << gsPlayback;
        std::cout << "\n cpp-setCurFrameId jmethodId: " << setCurrFrameId;
        env->CallVoidMethod(gsPlayback, setCurrFrameId, jCurrFrameId);
//    }
}

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initPlaybackLoopNative
  (JNIEnv* env, jobject _gsPlayback) {
  try {

      int currFrameId;
      currFrameId = 0;

      jclass gsPlayback = env->FindClass("dev/nateschieber/groovesprings/actors/GsPlaybackThread");

      std::cout << "\n gsPlayback " << gsPlayback;

      jmethodID setCurrFrameId = env->GetStaticMethodID (gsPlayback, "setCurrFrameId", "(Ljava/lang/Integer;)V");

      std::cout << "\n setCurrFrameId jmethodID: " << setCurrFrameId;
      std::cout << "\n Hey buddy \n";

      jclass jInteger = env->FindClass("java/lang/Integer");
      jmethodID jIntegerInit = env->GetMethodID(jInteger, "<init>", "(I)V");
      jobject jCurrFrameId = env->NewObject(jInteger, jIntegerInit, 5432);

      env->CallVoidMethod(gsPlayback, setCurrFrameId, jCurrFrameId);
//      std::thread playbackThread(playbackTask, env, gsPlayback, setCurrFrameId, currFrameId);

      std::this_thread::sleep_for(std::chrono::milliseconds(500));

      std::cout << "\n Hey Pal \n";


//      playbackThread.join();
   }
   catch(std::exception const& e)
   {
      std::cout << "Message: " << e.what() << "\n";
   }
}
