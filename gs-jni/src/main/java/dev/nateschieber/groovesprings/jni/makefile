### To create the header file
#   javac -h . JniMain.java

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so/.dylib library.
### To do so, edit the Run Configuration for the `gs_desktop_application` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni"

cxx = g++ -std=c++11
audio_src = audio/audio.cpp
audio_obj = audio/audio.o
vst3host_audiohost_src = audio/effects/vst3/host/audiohost/source/audiohost.cpp
vst3host_audiohost_obj = audio/effects/vst3/host/audiohost/source/audiohost.o
java_jni_main = JniMain.java
jni_main_header = dev_nateschieber_groovesprings_jni_JniMain.h
jni_src = dev_nateschieber_groovesprings_jni_JniMain.cpp
jni_obj = dev_nateschieber_groovesprings_jni_JniMain.o

# TODO: detect OS and set
# - for linux: linux
# - for macOS: darwin
os_java_include = darwin

# TODO: detect OS and set
# - for linux: -shared
# - for macOS: -dynamicLib
os_lib_flag = -dynamicLib

# TODO: detect OS and set
# - for linux:
# - for macO_sonoma: /opt/homebrew/Cellar
# NOTE:
#  - on MacOS it may be helpful to use `brew info portaudio` to view home directory
c_libs_home = /opt/homebrew/Cellar

vst3sdk_home = /Users/ns/code/vst3sdk

# TODO:
#   - version variables
c_libs_include_flags = -I$(c_libs_home)/libsndfile/1.2.2/include -I$(c_libs_home)/portaudio/19.7.0/include -I$(c_libs_home)/openjdk/22.0.1/include -I$(vst3sdk_home)

include_flags = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/$(os_java_include) ${c_libs_include_flags}
# link_flags = -lc -lsndfile -lportaudio
link_flags = -L$(c_libs_home)/libsndfile/1.2.2/lib -lsndfile -L$(c_libs_home)/portaudio/19.7.0/lib -lportaudio

# TODO: detect OS and set
# - for linux: libnative.so
# - for macOS: libnative.dynamicLib
lib = libnative.dylib

# TODO:
#   - more phony targets
#   - macOS_monterey, macOS_sonoma, linux

lib:
	javac -h . $(java_jni_main) SfInfo.java # produces jni_main_header
# 	$(cxx) -c -fPIC $(include_flags) $(vst3host_audiohost_src) -o $(vst3host_audiohost_obj)
	$(cxx) -c -fPIC $(include_flags) $(audio_src) -o $(audio_obj)
	$(cxx) -c -fPIC $(include_flags) $(jni_src) -o $(jni_obj)
	$(cxx) $(os_lib_flag) -fPIC -o $(lib) $(audio_obj) $(jni_obj) $(link_flags)

clean:
	rm $(jni_main_header)
	rm $(lib)
	rm $(audio_obj)
	rm $(jni_obj)
