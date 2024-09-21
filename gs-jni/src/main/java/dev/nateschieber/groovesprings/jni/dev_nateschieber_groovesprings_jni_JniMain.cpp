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
#include "./audio/effects/vst3/host/hostclasses.hpp"

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

/*
 * Class:     dev_nateschieber_groovesprings_jni_JniMain
 * Method:    initPlaybackLoopNative
 * Signature: (JLjava/lang/String;JJ)V
 */
JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initPlaybackLoopNative
  (JNIEnv* env, jobject _gsPlayback, jlong threadId, jstring jfile, jlong initialFrameId, jlong vst3HostPtr) {
  try {
      Audio audio(env, threadId, jfile, initialFrameId, vst3HostPtr);
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
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_allocVst3HostNative
  (JNIEnv *, jobject)
{
    // alloc vst3AudioHostApp
    Steinberg::Vst::AudioHost::App* vst3App;
    vst3App = new Steinberg::Vst::AudioHost::App;

    Steinberg::Vst::HostApplication vst3HostApp;

    char *uuid1 = (char*) "0123456789ABCDEF";
    char *uuid2 = (char*) "0123456789GHIJKL";
    auto obj = (void*) malloc( 1000 * sizeof( Steinberg::Vst::HostMessage) );
    vst3HostApp.createInstance(uuid1, uuid2, &obj);


    return (jlong) vst3App;
}

/*
 * Class:     dev_nateschieber_groovesprings_jni_JniMain
 * Method:    deleteVst3HostNative
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_deleteVst3HostNative
  (JNIEnv *, jobject, jlong appPtr)
{
    Steinberg::Vst::AudioHost::App* app = reinterpret_cast<Steinberg::Vst::AudioHost::App*>(appPtr);
    app->terminate();
    std::cout << "\n Vst3HostApp Termianted";
    return 0l;
}


/*
 * Class:     dev_nateschieber_groovesprings_jni_JniMain
 * Method:    initVst3HostNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_dev_nateschieber_groovesprings_jni_JniMain_initVst3HostNative
  (JNIEnv *, jobject, jlong appPtr)
{
    Steinberg::Vst::AudioHost::App *app = reinterpret_cast<Steinberg::Vst::AudioHost::App*>(appPtr);
    const std::vector<std::string> cmdArgs = {
        "/Users/ns/code/AnalogTapeModel/Plugin/build/CHOWTapeModel_artefacts/Release/VST3/CHOWTapeModel.vst3"
    };
    try {
        std::cout << "app init before" << std::endl;
        app->init(cmdArgs);
        std::cout << "app init after" << std::endl;
    } catch (...) {
        std::cout << "\n Could not Initialize Vst3HostApp.";
    }
}
