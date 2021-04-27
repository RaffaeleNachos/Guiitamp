cmake_minimum_required(VERSION 3.4.1)
project(giRi LANGUAGES C CXX)

# Build our own native library
# add_library (native-lib SHARED native-lib.cpp )

# library for live input to output
add_library(liveEffect
        SHARED
        LiveEffectEngine.cpp
        FullDuplexStream.cpp
        jni_bridge.cpp
        ./debug-utils/trace.cpp)

# libraries for audio effects
add_library(audioEffects
        STATIC
        ./BaseClasses/DelayEffectBase.cpp
        ./BaseClasses/FilterEffectBase.cpp
        ./BaseClasses/ModulationBaseClass.cpp
        ./DelayEffects/SimpleFlanger.cpp
        ./DelayEffects/SimpleDelay.cpp)

file(GLOB tskCPPS
        "./TSKEffects/*.cpp"
        )


add_library(tskEffects
        STATIC
        ${tskCPPS}
        )

target_include_directories(liveEffect
        PRIVATE
        ./debug-utils)

target_include_directories(audioEffects
        PRIVATE
        ./BaseClasses
        ./DelayEffects
        ./FilterEffects)

target_include_directories(tskEffects
        PRIVATE
        ./TSKEffects)


# Find the Oboe package
find_package (oboe REQUIRED CONFIG)

# Specify the libraries which our native library is dependent on, including Oboe
target_link_libraries(liveEffect
        PRIVATE
        audioEffects
        tskEffects
        android
        atomic
        log
        oboe::oboe)

# Enable optimization flags: if having problems with source level debugging,
# disable -Ofast ( and debug ), re-enable it after done debugging.
target_compile_options(liveEffect PRIVATE -Wall -Werror "$<$<CONFIG:RELEASE>:-Ofast>")
target_compile_options(audioEffects PRIVATE -Wall -Werror "$<$<CONFIG:RELEASE>:-Ofast>")
target_compile_options(tskEffects PRIVATE -Wall -Werror "$<$<CONFIG:RELEASE>:-Ofast>")

