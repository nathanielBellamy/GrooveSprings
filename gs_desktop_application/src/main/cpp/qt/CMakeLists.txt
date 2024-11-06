cmake_minimum_required(VERSION 3.29.6)

project(gsDisplay VERSION 0.0.0 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(LLVM_INSTALL_DIR /opt/homebrew/opt/llvm)


list(APPEND CMAKE_PREFIX_PATH "/Users/ns/code/qt6-build/qtbase/lib/cmake")
find_package(Qt6 REQUIRED COMPONENTS Core Gui Widgets)
qt_standard_project_setup()

qt_add_library(gsDisplay
        display.h
        display.cpp
)

target_link_libraries(gsDisplay PRIVATE Qt6::Core Qt6::Gui Qt6::Widgets)