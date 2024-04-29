# TODO - make this into an actual makefile

### In order to compile the Cpp source run the following two commands
### from within dev.nateschieber.groovesprings.audio.jni

#   g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux dev_nateschieber_groovesprings_audio_jni_JniMain.cpp -o dev_nateschieber_groovesprings_audio_jni_JniMain.o
#   g++ -c -fPIC -o libnative.so dev_nateschieber_groovesprings_audio_jni_JniMain.o -lc

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so library.
### To do so, edit the Run Configuration for the `gs-desktop-app` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-desktop-app/src/main/java/dev/nateschieber/groovesprings/audio/jni
