
#ifndef ATOMS_H
#define ATOMS_H

#include "./EnvelopeQtPtr.h"

CAF_BEGIN_TYPE_ID_BLOCK(groovesprings, first_custom_type_id)

  // typeIds
  CAF_ADD_TYPE_ID(groovesprings, (EnvelopeQtPtr))

  // atoms

  // Actor pairs (GsActorA, GsActorB) form a directed graph (oriented 1d CW complex) wherein
  //   - actors represent nodes
  //   - atoms name directed edges connecting nodes

  // (GsSupervisor, GsDisplay)
  CAF_ADD_ATOM(groovesprings, init_display_a)

  // (GsDisplay, GsSupervisor)
  CAF_ADD_ATOM(groovesprings, init_display_ar)

  CAF_ADD_ATOM(groovesprings, tc_trig_play_a)
  CAF_ADD_ATOM(groovesprings, tc_trig_play_ar)


CAF_END_TYPE_ID_BLOCK(groovesprings)

#endif //MAIN_H
