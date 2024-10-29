//
// Created by ns on 10/20/24.
//

#ifndef GSSUPERVISOR_H
#define GSSUPERVISOR_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

CAF_BEGIN_TYPE_ID_BLOCK(groovesprings, caf::first_custom_type_id)

  CAF_ADD_ATOM(my_project, add_atom)
  CAF_ADD_ATOM(my_project, multiply_atom)

CAF_END_TYPE_ID_BLOCK(groovesprings)

struct gs_supervisor_trait {

    using signatures = type_list<result<int32_t>(add_atom, int32_t, int32_t),
                                 result<int32_t>(multiply_atom, int32_t, int32_t)>;

};

using gs_supervisor_actor = typed_actor<gs_supervisor_trait>;

struct gs_supervisor_state {
     int32_t c = 3;

     gs_supervisor_actor::behavior_type make_behavior() {
       return {
           [](add_atom, int32_t a, int32_t b) { return a + b + c; },
           [](multiply_atom, int32_t a, int32_t b) { return a * b; }
       }
     };
}



#endif //GSSUPERVISOR_H
