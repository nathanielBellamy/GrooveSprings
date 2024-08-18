### To create the header file
#   javac -h . JniMain.java

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so/.dylib library.
### To do so, edit the Run Configuration for the `gs_desktop_application` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni"

cxx = g++ -std=c++20
audio_src = audio/audio.cpp
audio_obj = audio/audio.o
effects_src = audio/effects/gs_effect_reverb.cpp
effects_obj = audio/effects/gs_effect_reverb.o
java_jni_main = JniMain.java
jni_main_header = dev_nateschieber_groovesprings_jni_JniMain.h
jni_src = dev_nateschieber_groovesprings_jni_JniMain.cpp
jni_obj = dev_nateschieber_groovesprings_jni_JniMain.o
audio_deps = audio/audio.cpp audio/audio.h audio/audio_data.h audio/constants.h audio/effects/MVerb.h audio/effects/gs_effects_reverb.h
jni_deps = dev_nateschieber_groovesprings_jni_JniMain.cpp dev_nateschieber_groovesprings_jni_JniMain.h

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

# TODO:
#   - version variables
c_libs_include_flags = -I$(c_libs_home)/libsndfile/1.2.2/include -I$(c_libs_home)/portaudio/19.7.0/include -I$(c_libs_home)/openjdk/22.0.1/include

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
	$(cxx) -c -fPIC $(effects_src) -o $(effects_obj)
	$(cxx) -c -fPIC $(include_flags) $(audio_src) -o $(audio_obj)
	$(cxx) -c -fPIC $(include_flags) $(jni_src) -o $(jni_obj)
	$(cxx) $(os_lib_flag) -fPIC -o $(lib) $(effects_obj) $(audio_obj) $(jni_obj) $(link_flags)

clean:
	rm $(jni_main_header)
	rm $(lib)
	rm $(audio_obj)
	rm $(jni_obj)
