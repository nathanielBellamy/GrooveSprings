#include "jni.h"
#include<iostream>
#include "dev_nateschieber_groovesprings_audio_jni_JniMain.h"

JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_audio_jni_JniMain_helloWorld
  (JNIEnv* env, jobject thisObject) {
    std::cout << "Hello, Java! Your pal, C++ !!" << std::endl;
}
