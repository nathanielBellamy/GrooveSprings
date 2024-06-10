### To create the header file
#   javac -h . JniMain.java

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so/.dylib library.
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
os_java_include = darwin

# TODO: detect OS and set
# - for linux: -shared
# - for macOS: -dynamicLib
os_lib_flag = -dynamicLib

include_flags = -I${JAVA_HOME}/include -I${JAVA_HOME}/include/$(os_java_include)
link_flags = -lsndfile -lportaudio

# TODO: detect OS and set
# - for linux: -libnative.so
# - for macOS: -libnative.dynamicLib
lib = libnative.dylib

lib:
	$(cxx) -c -fPIC $(include_flags) $(audio_src) -o $(audio_obj)
	$(cxx) -c -fPIC $(include_flags) $(jni_src) -o $(jni_obj)
	$(cxx) $(os_lib_flag) -fPIC -o $(lib) $(audio_obj) $(jni_obj) -lc $(link_flags)

clean:
	rm $(lib)
	rm $(audio_obj)
	rm $(jni_obj)
