
### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so/.dylib library.
### To do so, edit the Run Configuration for the `gs_desktop_application` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/build"

cmake_minimum_required(VERSION 3.30.2)
project(native VERSION 0.0.0)

set (CMAKE_CXX_STANDARD 17)
set (JAVA_HOME "/opt/homebrew/opt/openjdk/bin")
set (VST3SDK_HOME "/Users/ns/code/vst3sdk")
# utilize vst3sdk cmake modules
set (CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${VST3SDK_HOME}/cmake/modules)
include(SMTG_DetectPlatform) # to detect platform

set (GS_JNI_WD "/Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/")
set (JAVA_JNI_MAIN "JniMain.java")
set (JAVA_SFINFO_MAIN "SfInfo.java")
set (JAVA_VST3AUDIOHOST_APP_PTR "Vst3AudioHostAppPtr.java")
# generate jni header
execute_process(
    COMMAND javac -h . ${JAVA_JNI_MAIN} ${JAVA_SFINFO_MAIN} ${JAVA_VST3AUDIOHOST_APP_PTR}# produces jni_main_header
    WORKING_DIRECTORY ${GS_JNI_WD}
)

set (AUDIO_SRC "audio/audio.cpp")
set (AUDIO_OBJ "build/audio/audio.cpp")
set (VST3HOST_SRC "audio/effects/vst3/host/audiohost/source/audiohost.cpp")
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
        -L${VST3SDK_HOME}/build/lib/Release
        -lsdk
        -L${VST3SDK_HOME}/build/lib/Release
        -lsdk_hosting
        -L${VST3SDK_HOME}/build/lib/Release
        -lpluginterfaces
)

add_library(audio_main OBJECT ${AUDIO_SRC})
target_compile_options(audio_main PRIVATE ${INCLUDE_FLAGS})
add_library(jni_main OBJECT ${JNI_SRC})
target_compile_options(jni_main PRIVATE ${INCLUDE_FLAGS})


# compile vst3 audiohost
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${VST3SDK_HOME}/cmake/modules)
add_subdirectory(audio/effects/vst3/host/audiohost)
target_link_libraries(audio_main PUBLIC audiohost)

# compile library
add_library(native SHARED)
set(LINK_LIBRARIES
    audio_main
    jni_main
    audiohost
    -L${VST3SDK_HOME}/build/lib/Release
    -lsdk_common
    ${LINK_FLAGS}
)

if (SMTG_MAC)
set (LINK_LIBRARIES "${LINK_LIBRARIES}
    -framework CoreFoundation
    -framework Foundation"
)
endif (SMTG_MAC)
target_link_libraries(native PUBLIC ${LINK_LIBRARIES})
