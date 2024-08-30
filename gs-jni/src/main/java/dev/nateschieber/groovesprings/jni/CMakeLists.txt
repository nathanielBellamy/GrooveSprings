
cmake_minimum_required(VERSION 3.30.2)
project(gs-jni VERSION 0.0.0)

set (CMAKE_CXX_STANDARD 11)
set (JAVA_HOME "/opt/homebrew/opt/openjdk/bin")
set (GS_JNI_WD "/Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/")
set (AUDIO_SRC "audio/audio.cpp")
set (AUDIO_OBJ "build/audio/audio.cpp")
set (VST3HOST_AUDIOHOST_SRC "audio/effects/vst3/host/audiohost/source/audiohost.cpp")
set (VST3HOST_AUDIOHOST_OBJ "build/audio/effects/vst3/host/audiohost/source/audiohost.o")
set (JAVA_JNI_MAIN "JniMain.java")
set (JAVA_SFINFO_MAIN "SfInfo.java")
set (JNI_MAIN_HEADER "dev_nateschieber_groovesprings_jni_JniMain.h")
set (JNI_SRC "dev_nateschieber_groovesprings_jni_JniMain.cpp")
set (JNI_OBJ "dev_nateschieber_groovesprings_jni_JniMain.o")

# TODO: detect OS and set
# - for linux: linux
# - for macOS: darwin
set (OS_JAVA_INCLUDE "darwin")

# TODO: detect OS and set
# - for linux: -shared
# - for macOS: -dynamicLib
set (OS_LIB_FLAG "-dynamicLib")


# TODO: detect OS and set
# - for linux:
# - for macO_sonoma: /opt/homebrew/Cellar
# NOTE:
#  - on MacOS it may be helpful to use `brew info portaudio` to view home directory
set (C_LIBS_HOME "/opt/homebrew/Cellar")

set (VST3SDK_HOME "/Users/ns/code/vst3sdk")

set (C_LIBS_INCLUDE_FLAGS
        -I${C_LIBS_HOME}/libsndfile/1.2.2/include
        -I${C_LIBS_HOME}/portaudio/19.7.0/include
        -I${C_LIBS_HOME}/openjdk/22.0.1/include
)

set (CXX_LIBS_INCLUDE_FLAGS
        -I${VST3SDK_HOME}
)

set (INCLUDE_FLAGS
        -I${JAVA_HOME}/include
        -I${JAVA_HOME}/include/${OS_JAVA_INCLUDE}
        ${C_LIBS_INCLUDE_FLAGS}
        ${CXX_LIBS_INCLUDE_FLAGS}
)

set (LINK_FLAGS
        -L${C_LIBS_HOME}/libsndfile/1.2.2/lib
        -lsndfile
        -L${C_LIBS_HOME}/portaudio/19.7.0/lib
        -lportaudio
)

set (CXX "g++ -std=11")

# TODO: detect OS and set
# - for linux: libnative.so
# - for macOS: libnative.dynamicLib
set (LIB libnative.dylib)

execute_process(
    COMMAND javac -h . ${JAVA_JNI_MAIN} ${JAVA_SFINFO_MAIN}
    COMMAND ${CXX} -c -fPIC ${INCLUDE_FLAGS} ${VST3HOST_AUDIOHOST_SRC} -o ${VST3HOST_AUDIOHOST_OBJ}
    WORKING_DIRECTORY ${GS_JNI_WD}
    COMMAND_ECHO STDOUT
 )