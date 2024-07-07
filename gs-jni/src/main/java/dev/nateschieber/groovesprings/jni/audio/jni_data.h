#ifndef JNI_DATA_H
#define JNI_DATA_H

struct JNI_DATA{
    JNIEnv* env;
    jclass gsPlayback;
    jmethodID getThreadId;
    jmethodID setCurrFrameId;
    jmethodID getPlayStateInt;
    jmethodID setPlayStateInt;
    jmethodID getPlaybackSpeedFloat;
    jmethodID setReadComplete;
    jclass jLong;
    jmethodID jLongInit;
    jclass jInteger;
    jmethodID jIntegerInit;

    JNI_DATA(JNIEnv* env) :
        env(env)
          , gsPlayback(env->FindClass("dev/nateschieber/groovesprings/actors/GsPlaybackThread"))
          , getThreadId(env->GetStaticMethodID (gsPlayback, "getThreadId", "()J"))
          , setCurrFrameId(env->GetStaticMethodID (gsPlayback, "setCurrFrameId", "(Ljava/lang/Long;)V"))
          , getPlayStateInt(env->GetStaticMethodID (gsPlayback, "getPlayStateInt", "()I"))
          , setPlayStateInt(env->GetStaticMethodID (gsPlayback, "setPlayStateInt", "(I)V"))
          , getPlaybackSpeedFloat(env->GetStaticMethodID (gsPlayback, "getPlaybackSpeedFloat", "()F"))
          , setReadComplete(env->GetStaticMethodID (gsPlayback, "setReadComplete", "(Z)V"))
          , jLong(env->FindClass("java/lang/Long"))
          , jLongInit(env->GetMethodID(jLong, "<init>", "(J)V"))
          , jInteger(env->FindClass("java/lang/Integer"))
          , jIntegerInit(env->GetMethodID(jInteger, "<init>", "(I)V")) {}
};

#endif
