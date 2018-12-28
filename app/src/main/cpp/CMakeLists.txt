cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_CXX11_EXTENSION_COMPILE_OPTION "-std=c++17")

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
             level-generator

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             Generator.cpp
             Map.cpp
             Overworld.cpp
             Perlin.cpp
             Random.cpp
             Tile.cpp
             Underground.cpp
             Interface.cpp

			 )
			 
# Specifies a path to native header files.
include_directories(${CMAKE_SOURCE_DIR}/include/)