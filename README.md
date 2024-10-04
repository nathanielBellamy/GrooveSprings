# GrooveSprings
### Music Library + Player Built With
- Akka
- portaudio
- libsndfile
- Angular
- ngrx
- Spring Boot

![GrooveSprings Use Example](./gs_demo_2.gif)

### Build
- frontend
  - `cd gs_desktop_application/src/main/frontend/angular/gs_desktop_frontend`
  - `npm run build-dist` 
    - outputs to folder matching current server configuration
  
- native lib
```bash
cd gs-jni/src/main/java/dev/nateschieber/groovesprings/jni
```
1. using makefile/gnu make or
```bash
make clean
make lib
```
2. using cmake

```bash
mkdir build
cd build
cmake -DCMAKE_BUILD_TYPE=Release ../
cmake --build .
```

---