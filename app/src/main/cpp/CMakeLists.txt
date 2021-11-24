cmake_minimum_required(VERSION 3.4.1)
project(Guiitamp LANGUAGES C CXX)

# Build our own native library
# add_library (native-lib SHARED native-lib.cpp )

# library for live input to output

file(GLOB tskCPPS
        "./TSKEffects/*.cpp"
        )

add_library(liveEffect
        SHARED
        LiveEffectEngine.cpp
        FullDuplexStream.cpp
        jni_bridge.cpp
        ./debug-utils/trace.cpp
        ./BaseClasses/DelayEffectBase.cpp
        ./BaseClasses/FilterEffectBase.cpp
        ./DelayEffects/SimpleFlanger.cpp
        ./DelayEffects/SimpleDelay.cpp
        ./BaseClasses/ModulationBaseClass.cpp
        ${tskCPPS}
        ./TSKEffects/Voicer.cpp
        ./AEfromArduino/AudioEffect.h
        )

target_include_directories(liveEffect
        PRIVATE
        ./debug-utils)


# Find the Oboe package
find_package (oboe REQUIRED CONFIG)

# Specify the libraries which our native library is dependent on, including Oboe
target_link_libraries(liveEffect
        PRIVATE
        android
        atomic
        log
        oboe::oboe)

# Enable optimization flags: if having problems with source level debugging,
# disable -Ofast ( and debug ), re-enable it after done debugging.
target_compile_options(liveEffect PRIVATE -Wall -Werror "$<$<CONFIG:RELEASE>:-Ofast>")

