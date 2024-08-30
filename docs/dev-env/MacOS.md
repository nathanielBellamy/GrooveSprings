# Dev Environment Setup
## MacOS

#### I. Initial Programs

- Browser of your choice/Firefox
- IntelliJ IDEA
- Homebrew

#### II. Homebrew it
```bash
brew install git npm node make gcc cmake portaudio jack libsndfile openjdk scala@3.3 docker docker-compose colima
```

#### III. Clone repo
```bash
git clone git@github.com:nathanielBellamy/GrooveSprings.git
```

#### IV. Open Repo in IntelliJ

#### V. Install Scala Plugin

#### VI. Set JDK in IntelliJ Project Structure

- File -> Project Structure
- as of writing:
  - openjdk: stable 22.0.1
  - `/opt/homebrew/opt/openjdk/libexec/openjdk.jdk` (as of writing)

#### VII. Build Frontend

```bash
cd gs_desktop_application/src/main/frontend/angular/gs_desktop_application
npm install
npm run build-dist
```

#### VIII. Build Native Library
- using makefile (VST3 not supported)
```bash
cd gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
make lib
```
- using cmake
```bash
cd gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
mkdir build
cd build
cmake -DCMAKE_BUILD_CONFIG=Release ../
cmake --build .
```

#### IX. Create Run Configurations
- run applications: 
  - `GsDesktopApplication`
  - `GsTrackServiceApplication`
  - `GsPriceServiceApplication`
- create a Compound Run Configuration including each of the generated configurations
- development note: `GsDesktopApplication` restarts fast, the others restart slow
- to test: with `GsDesktopApplication` running, open browser to `localhost:5678`, frontend SPA should load

#### X. Define VM Option for Native Lib in Run Config
- edit the `GsDesktopApplication` run configuration
- `Modify Options` -> `Add VM Options`
- update and add the following VM option
  - if built using makefile (no VST3)
```
-Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni"
```
  - if built using cmake
```
-Djava.library.path="/absolute/path/to/repo/GrooveSprings/gs-jni/src/main/java/dev/nateschieber/groovesprings/jni/build"
```

#### XI. Setup VST3SDK

- follow build instructions here: https://github.com/steinbergmedia/vst3sdk
  - you may need to install xcode (😔) even when building "without xcode"
  - after installing xcode from the AppStore, if cmake has trouble finding xcode, try
```bash
sudo xcode-select --reset
```
  - update the makefile with the location of your vst3sdk build

#### XI. Establish Library + Add Music
- create library director
```bash
mkdir ~/GrooveSprings_MusicLibrary
```
- add your own music files in

#### XII. Start Docker Using Colima
```bash
colima start
```

#### XIII. Run the App
- run the compound `GrooveSprings` run configuration
- bring music into the app by running a library scan: `Settings -> Scan Library`
