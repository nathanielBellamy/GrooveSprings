# TODO - make this into an actual makefile

### To create the header file
#   javac -h . JniMain.java

### In order to compile the Cpp source run the following two commands
### from within dev.nateschieber.groovesprings.jni

#   g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux dev_nateschieber_groovesprings_jni_JniMain.cpp -o dev_nateschieber_groovesprings_jni_JniMain.o
#   g++ -shared -fPIC -o libnative.so dev_nateschieber_groovesprings_jni_JniMain.o -lc

### In order to run this program within Intellij
### we need to tell the JVM where to find the the compiled .so library.
### To do so, edit the Run Configuration for the `gs_desktop_application` by adding the following VM option
### -Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
