//
// Created by ns on 10/20/24.
//

#ifndef GSSUPERVISOR_H
#define GSSUPERVISOR_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "../atoms.h"

using namespace caf;

struct gs_supervisor_trait {

    using signatures = type_list<result<int32_t>(add_a, int32_t, int32_t),
                                 result<int32_t>(multiply_a, int32_t, int32_t)>;

};

using gs_supervisor = typed_actor<gs_supervisor_trait>;

struct gs_supervisor_state {
     int32_t c;

     gs_supervisor::pointer self;

     gs_supervisor_state(gs_supervisor::pointer self, int32_t c) :
             self(self)
           , c(c)
             {}

     gs_supervisor::behavior_type make_behavior() {
       return {
           [this](add_a, int32_t a, int32_t b) {
             std::cout << "wowza a: " << a << " b: " << b << std::endl;
             return a + b + c;
           },
           [this](multiply_a, int32_t a, int32_t b) { return a * b * c; }
       };
     };
};

#endif //GSSUPERVISOR_H
