# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.6.0)
add_definitions( -funroll-loops -finline-functions -ffunction-sections -fdata-sections -Ofast -ftree-vectorize -DHAVE_CONFIG_H -DNDEBUG )

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( libFLAC
             SHARED
             libFLAC/bitmath.c 
			 libFLAC/bitreader.c 
			 libFLAC/bitwriter.c 
			 libFLAC/cpu.c 
			 libFLAC/crc.c 
			 libFLAC/fixed.c 
			 libFLAC/float.c 
			 libFLAC/format.c
			 libFLAC/lpc.c
			 libFLAC/memory.c
			 libFLAC/md5.c
			 libFLAC/metadata_object.c
			 libFLAC/stream_decoder.c
			 libFLAC/stream_encoder.c
			 libFLAC/stream_encoder_framing.c
			 libFLAC/window.c)

include_directories(  ./include ./libFLAC/include )