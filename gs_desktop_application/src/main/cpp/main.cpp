//
// Created by ns on 9/30/24.
//


#include "main.h"

#include <chrono>
#include <string>
#include <thread>
#include <iostream>
#include <string>

#include "caf/actor_from_state.hpp"
#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "./actors/GsSupervisor.h"

#include "./audio/audio.h"
#include "./audio/effects/vst3/host/audiohost/source/audiohost.h"
#include "./audio/effects/vst3/host/hostclasses.hpp"
#include "./audio/effects/vst3/host/editorhost/source/editorhost.h"

namespace GrooveSprings {

using namespace caf;
using namespace std::literals;

// Hello, CAF!
// https://www.actor-framework.org//static/doxygen/1.0.0/hello_world_8cpp-example.html

void hello_world(event_based_actor* self, const gs_supervisor& buddy) {
  self->mail(add_a{}, 1, 2)
    .request(buddy, infinite)
    .then(
        [self](int32_t result) {
            std::cout << "gs_supervisor result: " << result << std::endl;
        });
}

void caf_main(actor_system& sys, Steinberg::Vst::AudioHost::App* vst3AudioHost) {
  auto gs_supervisor = sys.spawn(actor_from_state<gs_supervisor_state>, sys, 5);

  sys.spawn(hello_world, gs_supervisor);

  Audio audio(
      sys,
      1l,
      "/Users/ns/GrooveSprings_MusicLibrary/Amy Winehouse/Back to Black/Amy Winehouse - Back to Black (2006) [FLAC]/06 Love Is A Losing Game.flac",
      0l,
      vst3AudioHost
  );

//  audio.run();
}

extern "C" {

//    CAF_MAIN();

    int main() {
        // vst3host needs to be instantiated on the main thread

        // alloc vst3AudioHostApp
        Steinberg::Vst::AudioHost::App* vst3AudioHost;
        vst3AudioHost = new Steinberg::Vst::AudioHost::App;

        Steinberg::Vst::HostApplication vst3HostApp;

        char *uuid1 = (char*) "0123456789ABCDEF";
        char *uuid2 = (char*) "0123456789GHIJKL";
        auto obj = (void*) malloc( 1000 * sizeof( Steinberg::Vst::HostMessage) );
        vst3HostApp.createInstance(uuid1, uuid2, &obj);

        Steinberg::Vst::EditorHost::App editorApp;
        const auto& cmdArgs = std::vector<std::string> {
            "/Library/Audio/Plug-Ins/VST3/ValhallaSupermassive.vst3"
//            "/Users/ns/code/AnalogTapeModel/Plugin/build/CHOWTapeModel_artefacts/Release/VST3/CHOWTapeModel.vst3"
        };
        editorApp.init (cmdArgs);

        // Initialize the global type information before anything else.
        core::init_global_meta_objects();
        // Create the config.
        actor_system_config cfg;
        // Create the actor system.
        actor_system sys{cfg};
        // Run user-defined code.
        caf_main(sys, vst3AudioHost);

        // TODO: loop to check for exit
        std::this_thread::sleep_for(std::chrono::seconds(10));

        return 0;
    }
}

} // GrooveSprings