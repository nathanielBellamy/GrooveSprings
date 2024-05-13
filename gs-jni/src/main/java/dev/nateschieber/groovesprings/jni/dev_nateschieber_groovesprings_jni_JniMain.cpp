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

void jSetCurrFrameId(JNIEnv* env, jobject gsPlayback, jmethodID setCurrFrameId, int currFrameId)
{
      // TODO: reuse jInteger and jIntegerInit
      jclass jInteger = env->FindClass("java/lang/Integer");
      jmethodID jIntegerInit = env->GetMethodID(jInteger, "<init>", "(I)V");
      jobject jCurrFrameId = env->NewObject(jInteger, jIntegerInit, currFrameId);

      env->CallVoidMethod(gsPlayback, setCurrFrameId, jCurrFrameId);
}

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initPlaybackLoopNative
  (JNIEnv* env, jobject _gsPlayback) {
  try {

      int currFrameId;
      currFrameId = 0;

      jclass gsPlayback = env->FindClass("dev/nateschieber/groovesprings/actors/GsPlaybackThread");
      jmethodID setCurrFrameId = env->GetStaticMethodID (gsPlayback, "setCurrFrameId", "(Ljava/lang/Integer;)V");

      while (true) {
          currFrameId += 1;
          jSetCurrFrameId(env, gsPlayback, setCurrFrameId, currFrameId);
      }
   }
   catch(std::exception const& e)
   {
      std::cout << "Message: " << e.what() << "\n";
   }
}
