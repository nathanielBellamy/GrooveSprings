#ifndef JNI_DATA_H
#define JNI_DATA_H

typedef struct {
    JNIEnv* env;
    jclass gsPlayback;
    jmethodID setCurrFrameId;
    jmethodID getStopped;
    jclass jNum;
    jmethodID jNumInit;
} JNI_DATA;

#endif
