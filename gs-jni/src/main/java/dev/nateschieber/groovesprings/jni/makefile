# TODO - make this into an actual makefile

### To create the header file
#   javac -h . JniMain.java

### In order to compile the Cpp source run the following two commands
### from within dev.nateschieber.groovesprings.jni

# LINUX
#   g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux audio/audio.cpp -o audio/audio.o
#   g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux dev_nateschieber_groovesprings_jni_JniMain.cpp -o dev_nateschieber_groovesprings_jni_JniMain.o
#   g++ -shared -fPIC -o libnative.so audio/audio.o dev_nateschieber_groovesprings_jni_JniMain.o -lc -lsndfile -lportaudio

# MacOS
#  g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin audio/audio.cpp -o audio/audio.o
#  g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin dev_nateschieber_groovesprings_jni_JniMain.cpp -o dev_nateschieber_groovesprings_jni_JniMain.o
#  g++ -dynamicLib -o libnative.dylib audio/audio.o dev_nateschieber_groovesprings_jni_JniMain.o -lc -lsndfile -lportaudio

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so library.
### To do so, edit the Run Configuration for the `gs_desktop_application` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/dev_nateschieber_groovesprings_jni_JniMain"
