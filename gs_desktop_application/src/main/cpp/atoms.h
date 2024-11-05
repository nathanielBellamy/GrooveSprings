
#ifndef ATOMS_H
#define ATOMS_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

using namespace caf;

CAF_BEGIN_TYPE_ID_BLOCK(groovesprings, first_custom_type_id)

  // Actor pairs (GsActorA, GsActorB) form a directed graph (oriented 1d CW complex) wherein
  //   - actors represent nodes
  //   - atoms name directed edges connecting nodes

  // (GsSupervisor, GsDisplay)
  CAF_ADD_ATOM(groovesprings, init_display_a)

  // (GsDisplay, GsSupervisor)
  CAF_ADD_ATOM(groovesprings, init_display_ar)


CAF_END_TYPE_ID_BLOCK(groovesprings)

#endif //MAIN_H
