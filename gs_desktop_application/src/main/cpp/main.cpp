//
// Created by ns on 9/30/24.
//

#include "jni.h"
#include <iostream>
#include "main.h"

extern "C" {
    int main() {
        std::cout << "Welcome to GrooveSprings." << std::endl;
        JavaVM *jvm;       /* denotes a Java VM */
        JNIEnv *env;       /* pointer to native method interface */
        JavaVMInitArgs vm_args; /* JDK/JRE 6 VM initialization arguments */
        JavaVMOption* options = new JavaVMOption[2];
        options[0].optionString = "-Djava.class.path=/Users/ns/code/GrooveSprings/gs_desktop_application/src/main/java/dev/nateschieber/groovesprings";
        options[1].optionString = "-Djava.library.path=/Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/jni/build-dist";
        vm_args.version = JNI_VERSION_1_6;
        vm_args.nOptions = 2;
        vm_args.options = options;
        vm_args.ignoreUnrecognized = false;
        /* load and initialize a Java VM, return a JNI interface
         * pointer in env */
        // TODO: debug linking JNI_CreateJavaVM
        jint res = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
        delete options;
        /* invoke the Main.test method using the JNI */
        jclass cls = env->FindClass("GsDesktopApplication");
        jmethodID mid = env->GetStaticMethodID(cls, "main", "()V");
        env->CallStaticVoidMethod(cls, mid);
        /* We are done. */
        jvm->DestroyJavaVM();
    }
}
