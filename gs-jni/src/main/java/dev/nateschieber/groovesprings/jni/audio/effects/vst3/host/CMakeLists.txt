

cmake_minimum_required(VERSION 3.29.6)
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${VST3SDK_HOME}/cmake/modules)

add_subdirectory(audiohost)
add_subdirectory(editorhost)

add_library(vst3host
        hostclasses.hpp
        hostclasses.cpp)
target_compile_options(vst3host PRIVATE -I${VST3SDK_HOME})
