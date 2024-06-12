#ifndef JNI_DATA_H
#define JNI_DATA_H

struct JNI_DATA{
    JNIEnv* env;
    jclass gsPlayback;
    jmethodID setCurrFrameId;
    jmethodID getPlayStateInt;
    jmethodID getPlaybackSpeedFloat;
    jclass jNum;
    jmethodID jNumInit;

    JNI_DATA(JNIEnv* env) :
        env(env)
          , gsPlayback(env->FindClass("dev/nateschieber/groovesprings/actors/GsPlaybackThread"))
          , setCurrFrameId(env->GetStaticMethodID (gsPlayback, "setCurrFrameId", "(Ljava/lang/Long;)V"))
          , getPlayStateInt(env->GetStaticMethodID (gsPlayback, "getPlayStateInt", "()I"))
          , getPlaybackSpeedFloat(env->GetStaticMethodID (gsPlayback, "getPlaybackSpeedFloat", "()F"))
          , jNum(env->FindClass("java/lang/Long"))
          , jNumInit(env->GetMethodID(jNum, "<init>", "(J)V")) {}
};

#endif
