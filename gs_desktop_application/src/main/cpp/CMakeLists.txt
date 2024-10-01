
cmake_minimum_required(VERSION 3.29.6)
project(GrooveSprings VERSION 0.0.0)

set (CMAKE_CXX_STANDARD 17)
set (JAVA_HOME "/opt/homebrew/Cellar/openjdk/22.0.1/bin")
set (VST3SDK_HOME "/Users/ns/code/vst3sdk")

set (INCLUDE_FLAGS
        -I/opt/homebrew/Cellar/openjdk/22.0.1/include
        -I${JAVA_HOME}/include
#        -I/opt/homebrew/opt/openjdk/bin/include
#        -I/opt/homebrew/opt/openjdk/bin/include/darwin
)
# utilize vst3sdk cmake modules
list (APPEND CMAKE_MODULE_PATH "${VST3SDK_HOME}/cmake/modules")
include(SMTG_DetectPlatform)


add_subdirectory(
    /Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
    /Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/build-dist
)
add_executable(groovesprings
        main.h
        main.cpp)
target_link_libraries(groovesprings
        PUBLIC
        native
)
target_compile_options(groovesprings PRIVATE ${INCLUDE_FLAGS})
set_target_properties(groovesprings PROPERTIES LINKER_LANGUAGE CXX)
