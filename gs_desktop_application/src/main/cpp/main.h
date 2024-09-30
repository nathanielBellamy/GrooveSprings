#ifndef MAIN_H
#define MAIN_H

#include <jni.h>
#include <iostream>

int inline main() {
    JavaVM *jvm;       /* denotes a Java VM */
    JNIEnv *env;       /* pointer to native method interface */
    JavaVMInitArgs vm_args; /* JDK/JRE 6 VM initialization arguments */
    JavaVMOption* options = new JavaVMOption[1];
    options[0].optionString = "-Djava.class.path=/Users/ns/code/Groovesprings/gs_desktop_application/src/main/scala/dev/groovesprings/gs_desktop_application -Djava.library.path=/Users/ns/code/Groovesprings/gs-jni/src/main/java/dev/groovesprings/jni/build-dist";
    vm_args.version = JNI_VERSION_1_6;
    vm_args.nOptions = 1;
    vm_args.options = options;
    vm_args.ignoreUnrecognized = false;
    /* load and initialize a Java VM, return a JNI interface
     * pointer in env */
    std::cout<< "Hello about to init jvm" << std::endl;
    JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
    delete options;
    /* invoke the Main.test method using the JNI */
    jclass cls = env->FindClass("Main");
    jmethodID mid = env->GetStaticMethodID(cls, "test", "(I)V");
    env->CallStaticVoidMethod(cls, mid, 100);

    std::cout<< "Hello done initializing jvm" << std::endl;

    /* We are done. */
    jvm->DestroyJavaVM();
}

#endif