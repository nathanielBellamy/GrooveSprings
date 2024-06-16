#include "jni.h"
#include <chrono>
#include <iostream>
#include <string>
#include <thread>
#include <sndfile.hh>
#include <portaudio.h>
#include "dev_nateschieber_groovesprings_jni_JniMain.h"
#include "./audio/audio.h"
#include "./audio/sf_info_reader.h"

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

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initPlaybackLoopNative
  (JNIEnv* env, jobject _gsPlayback, jstring jfile, jlong initialFrameId) {
  try {
      Audio audio(env, jfile, initialFrameId);
      audio.run();
   }
   catch(std::exception const& e)
   {
      std::cout << "Message: " << e.what() << "\n";
   }
}


/*
 * Class:     dev_nateschieber_groovesprings_jni_JniMain
 * Method:    readSfInfoNative
 * Signature: (Ljava/lang/String;)Ldev/nateschieber/groovesprings/jni/JniMain/SfInfo;
 */
JNIEXPORT jobject JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_readSfInfoNative
  (JNIEnv *env, jobject _, jstring filePath)
{
    std::cout<<"\nHello from readSfInfoNative \n";
    SfInfoReader sfInfoReader(env, filePath);
    SF_INFO sf_info = sfInfoReader.read();
    std::cout << "sfInfoReader.sf_info.samplerate: " << sf_info.samplerate << "\n";

    return sfInfoReader.jWrap(sf_info);
}
