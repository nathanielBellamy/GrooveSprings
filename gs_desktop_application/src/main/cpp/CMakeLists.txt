
cmake_minimum_required(VERSION 3.29.6)
project(groovesprings VERSION 0.0.0)

set (CMAKE_CXX_STANDARD 17)
set (JAVA_HOME "/opt/homebrew/opt/openjdk/bin")
set (VST3SDK_HOME "/Users/ns/code/vst3sdk")
# utilize vst3sdk cmake modules
list (APPEND CMAKE_MODULE_PATH "${VST3SDK_HOME}/cmake/modules")
include(SMTG_DetectPlatform)


add_subdirectory(
    /Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
    /Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/build-dist
)
add_executable(groovesprings
        main.h)
target_link_libraries(groovesprings native)
set_target_properties(groovesprings PROPERTIES LINKER_LANGUAGE CXX)
