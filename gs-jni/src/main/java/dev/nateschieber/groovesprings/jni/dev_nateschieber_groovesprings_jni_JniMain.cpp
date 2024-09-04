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
#include "./audio/effects/vst3/host/audiohost/source/audiohost.h"

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
  (JNIEnv* env, jobject _gsPlayback, jlong threadId, jstring jfile, jlong initialFrameId) {
  try {
      Audio audio(env, threadId, jfile, initialFrameId);
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
    SfInfoReader sfInfoReader(env, filePath);
    SF_INFO sf_info = sfInfoReader.read();

    return sfInfoReader.jWrap(sf_info);
}

/*
 * Class:     dev_nateschieber_groovesprings_jni_JniMain
 * Method:    allocVst3HostNative
 * Signature: ()Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_allocVst3HostNative
  (JNIEnv *env, jobject)
{
    // alloc vst3AudioHostApp
    Steinberg::Vst::AudioHost::App *vst3AudioHostAppPtr = new Steinberg::Vst::AudioHost::App;
    const std::vector<std::string> cmdArgs = {
        "/Users/ns/code/AnalogTapeModel/Plugin/build/CHOWTapeModel_artefacts/Release/VST3/CHOWTapeModel.vst3"
    };
//    vst3AudioHostAppPtr->init(cmdArgs);

    // construct pointer wrapper to return to JNI
    jclass jVst3AudioHostAppPtrClass = env->FindClass("dev/nateschieber/groovesprings/jni/Vst3AudioHostAppPtr");
    jmethodID jVst3AudioHostAppPtrConstr = env->GetMethodID(jVst3AudioHostAppPtrClass, "<init>", "(I)V");
    jobject jVst3AudioHostAppPtr = env->NewObject(
        jVst3AudioHostAppPtrClass,
        jVst3AudioHostAppPtrConstr,
        vst3AudioHostAppPtr
    );

    return jVst3AudioHostAppPtr;
}

/*
 * Class:     dev_nateschieber_groovesprings_jni_JniMain
 * Method:    initVst3HostNative
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initVst3HostNative
  (JNIEnv *, jobject, jint vst3AudioHostAppAddress)
{
    std::cout << "\n Addrr recevied: " << vst3AudioHostAppAddress;
    Steinberg::Vst::AudioHost::App *app = reinterpret_cast<Steinberg::Vst::AudioHost::App*>(vst3AudioHostAppAddress);
    const std::vector<std::string> cmdArgs = {
        "/Users/ns/code/AnalogTapeModel/Plugin/build/CHOWTapeModel_artefacts/Release/VST3/CHOWTapeModel.vst3"
    };
    try {
        app->init(cmdArgs);
    } catch (...) {
        std::cout << "\n Could not retrieve Vst3HostApp from pointer";
    }
}
