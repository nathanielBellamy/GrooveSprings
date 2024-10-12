//
// Created by ns on 9/30/24.
//


#include <chrono>
#include <string>
#include <thread>
#include "./audio/effects/vst3/host/audiohost/source/audiohost.h"
#include "./audio/effects/vst3/host/hostclasses.hpp"
#include "./audio/effects/vst3/host/editorhost/source/editorhost.h"


#include <iostream>
#include <string>
#include "main.h"

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

namespace GrooveSprings {

using namespace caf;
using namespace std::literals;

// Hello, CAF!
// https://www.actor-framework.org//static/doxygen/1.0.0/hello_world_8cpp-example.html

behavior mirror(event_based_actor* self) {
    return {
        [self](const std::string& what) -> std::string {
            self->println("{}", what);
            return std::string{what.rbegin(), what.rend()};
        }
    };
}

void hello_world(event_based_actor* self, const actor& buddy) {
  self->mail("Hello World!")
    .request(buddy, 10s)
    .then(
        [self](const std::string& what) {
            self->println("{}", what);
        });
}

void caf_main(actor_system& sys) {
  auto mirror_actor = sys.spawn(mirror);
  sys.spawn(hello_world, mirror_actor);
}

extern "C" {

//    CAF_MAIN();

    int main() {
        // vst3host needs to be instantiated on the main thread

        // alloc vst3AudioHostApp
        Steinberg::Vst::AudioHost::App* vst3App;
        vst3App = new Steinberg::Vst::AudioHost::App;

        Steinberg::Vst::HostApplication vst3HostApp;

        char *uuid1 = (char*) "0123456789ABCDEF";
        char *uuid2 = (char*) "0123456789GHIJKL";
        auto obj = (void*) malloc( 1000 * sizeof( Steinberg::Vst::HostMessage) );
        vst3HostApp.createInstance(uuid1, uuid2, &obj);

        Steinberg::Vst::EditorHost::App editorApp;
        const auto& cmdArgs = std::vector<std::string> {""};
        editorApp.init (cmdArgs);


        // Initialize the global type information before anything else.
        core::init_global_meta_objects();
        // Create the config.
        actor_system_config cfg;
        // Create the actor system.
        actor_system sys{cfg};
        // Run user-defined code.
        caf_main(sys);

        return 0;
    }
}

} // GrooveSprings