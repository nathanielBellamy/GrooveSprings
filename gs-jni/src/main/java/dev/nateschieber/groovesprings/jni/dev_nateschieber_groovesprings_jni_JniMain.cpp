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

void jSetCurrFrameId(
    JNIEnv* env,
    jobject gsPlayback,
    jmethodID setCurrFrameId,
    jclass jNum,
    jmethodID jNumInit,
    int currFrameId
){
   jobject jCurrFrameId = env->NewObject(jNum, jNumInit, currFrameId);
   env->CallVoidMethod(gsPlayback, setCurrFrameId, jCurrFrameId);
}

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initPlaybackLoopNative
  (JNIEnv* env, jobject _gsPlayback) {
  try {
      long currFrameId;
      currFrameId = 0;

      jclass gsPlayback = env->FindClass("dev/nateschieber/groovesprings/actors/GsPlaybackThread");
      jmethodID setCurrFrameId = env->GetStaticMethodID (gsPlayback, "setCurrFrameId", "(Ljava/lang/Long;)V");
      jmethodID getStopped = env->GetStaticMethodID (gsPlayback, "getStopped", "()Z");

      jclass jNum = env->FindClass("java/lang/Long");
      jmethodID jNumInit = env->GetMethodID(jNum, "<init>", "(J)V");
      bool stopped;

      while (true) {
          currFrameId += 1;
          stopped = env->CallStaticBooleanMethod(gsPlayback, getStopped);
          if (stopped)
          {
              jSetCurrFrameId(
                env,
                gsPlayback,
                setCurrFrameId,
                jNum,
                jNumInit,
                0
              );
              break;
          }
          if (currFrameId % 1000 == 0)
          {
              jSetCurrFrameId(
                env,
                gsPlayback,
                setCurrFrameId,
                jNum,
                jNumInit,
                currFrameId
              );
          }
      }
   }
   catch(std::exception const& e)
   {
      std::cout << "Message: " << e.what() << "\n";
   }
}
