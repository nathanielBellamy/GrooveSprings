# TODO - make this into an actual makefile

### To create the header file
#   javac -h . JniMain.java

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so library.
### To do so, edit the Run Configuration for the `gs_desktop_application` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/dev_nateschieber_groovesprings_jni_JniMain"

cxx = g++
audio_src = audio/audio.cpp
audio_obj = audio/audio.o
jni_src = dev_nateschieber_groovesprings_jni_JniMain.cpp
jni_obj = dev_nateschieber_groovesprings_jni_JniMain.o
audio_deps = audio/audio.cpp audio/audio.h audio/audio_data.h audio/constants.h
jni_deps = dev_nateschieber_groovesprings_jni_JniMain.cpp dev_nateschieber_groovesprings_jni_JniMain.h

# TODO: detect OS and set
# - for linux: linux
# - for macOS: darwin
os_java_include = linux

# TODO: detect OS and set
# - for linux: -shared
# - for macOS: -dynamicLib
os_lib_flag = -shared

include_flags = -I${JAVA_HOME}/include -I${JAVA_HOME}/include/$(os_java_include)
link_flags = -lsndfile -lportaudio

lib = libnative.so

$(audio_obj) : $(audio_deps)
	$(cxx) -c -fPIC $(include_flags) $(audio_src) -o $(audio_obj)

$(jni_obj) : $(jni_deps)
	$(cxx) -c -fPIC $(include_flags) $(jni_src) -o $(jni_obj)

$(lib) : $(audio_deps) $(jni_deps)
	$(cxx) $(os_lib_flag) -fPIC -o $(lib) $(objects) -lc $(link_flags)
