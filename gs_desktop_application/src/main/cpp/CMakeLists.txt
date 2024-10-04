
cmake_minimum_required(VERSION 3.29.6)
project(GrooveSprings VERSION 0.0.0)

set (CMAKE_CXX_STANDARD 17)
#set (JAVA_HOME "/usr/local/Cellar/openjdk/23")
set (VST3SDK_HOME "/Users/ns/code/vst3sdk")

set (INCLUDE_FLAGS
        -I/Users/ns/code/actor-framework/libcaf_core
        -I/Users/ns/code/actor-framework/libcaf_io
)
# utilize vst3sdk cmake modules
list (APPEND CMAKE_MODULE_PATH "${VST3SDK_HOME}/cmake/modules")
include(SMTG_DetectPlatform)

set (CAF_ROOT
        /Users/ns/code/actor-framework/build
)
find_package (CAF
        REQUIRED)

#add_subdirectory(
#    /Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
#    /Users/ns/code/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/build-dist
#)

add_executable(groovesprings
        main.h
        main.cpp)
target_link_libraries(groovesprings
        PUBLIC
        CAF::core
#        native
)
target_compile_options(groovesprings PRIVATE ${INCLUDE_FLAGS})
set_target_properties(groovesprings PROPERTIES LINKER_LANGUAGE CXX)
