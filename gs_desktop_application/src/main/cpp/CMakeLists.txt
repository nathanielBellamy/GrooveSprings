
cmake_minimum_required(VERSION 3.29.6)
project(GrooveSprings VERSION 0.0.0)

set (CMAKE_CXX_STANDARD 17)
set (CMAKE_CXX_STANDARD_REQUIRED ON)
#set (JAVA_HOME "/usr/local/Cellar/openjdk/23")
set (VST3SDK_HOME "/Users/ns/code/vst3sdk")
set (C_LIBS_HOME "/opt/homebrew/Cellar")

#set(QT_NO_STANDARD_PROJECT_SETUP TRUE)

find_package(Qt6 REQUIRED COMPONENTS Core Widgets)
if (Qt6_FOUND)
    message(INFO "--Qt6--FOUND--Top--Level--")
endif()

qt_standard_project_setup()
set (INCLUDE_FLAGS
        -I/Users/ns/code/actor-framework/libcaf_core
        -I/Users/ns/code/actor-framework/libcaf_io
        -I${VST3SDK_HOME}
        -I${C_LIBS_HOME}/libsndfile/1.2.2/include
        -I${C_LIBS_HOME}/portaudio/19.7.0/include
)
# utilize vst3sdk cmake modules
list (APPEND CMAKE_MODULE_PATH "${VST3SDK_HOME}/cmake/modules")
include(SMTG_DetectPlatform)

set (CAF_ROOT
        /Users/ns/code/actor-framework/build
)
find_package (CAF
        REQUIRED)

add_subdirectory(
    /Users/ns/code/GrooveSprings/gs_desktop_application/src/main/cpp/audio
)
add_subdirectory(
        /Users/ns/code/GrooveSprings/gs_desktop_application/src/main/cpp/qt
)

add_executable(groovesprings
        main.h
        main.cpp
        atoms.h
        actors/GsSupervisor.h
        actors/GsDisplay.h
        qt/display.h)
target_link_libraries(groovesprings
        PUBLIC
        CAF::core
        gsAudio
        gsDisplay
        Qt6::Widgets
)
target_compile_options(groovesprings PRIVATE ${INCLUDE_FLAGS})
set_target_properties(groovesprings PROPERTIES LINKER_LANGUAGE CXX)
