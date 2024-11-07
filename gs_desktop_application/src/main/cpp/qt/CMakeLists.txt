cmake_minimum_required(VERSION 3.29.6)

project(gsDisplay VERSION 0.0.0 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(LLVM_INSTALL_DIR /opt/homebrew/opt/llvm)


list(APPEND CMAKE_PREFIX_PATH "/Users/ns/code/qt6-build/qtbase/lib/cmake")
find_package(Qt6 REQUIRED COMPONENTS Core Widgets)
if (Qt6_FOUND)
    message(INFO "--Qt6--FOUND--")
endif()
qt_standard_project_setup()

qt_add_library(gsDisplay STATIC
        display.h
        display.cpp
)

target_compile_options(gsDisplay
        PRIVATE
        -I/Users/ns/code/qt6-build/qtbase/include)
target_link_libraries(gsDisplay PRIVATE Qt6::Core Qt6::Widgets)
