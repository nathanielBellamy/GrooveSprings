# Dev Environment Setup
## MacOS

#### I. Initial Programs

- Browser of your choice/Firefox
- IntelliJ IDEA
- Homebrew
```bash
brew install brew
```

#### II. Homebrew it
```bash
brew install git npm node make gcc portaudio libsndfile openjdk scala@3.3 docker docker-compose colima
```

#### III. Clone repo
```bash
git clone
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
```bash
cd gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
make lib
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
- copy in the command-line argument from the comment in the makefile, updating the absolute path the `GrooveSprings` repository


#### XI. Establish Library + Add Music
- create library director
```bash
mkdir ~/GrooveSprings_MusicLibrary
```
- add your own music files in


#### XII. Run the app
- make sure `docker` is running through `colima`
```bash
colima start
```
- run the compound `GrooveSprings` configuration