
#ifndef ATOMS_H
#define ATOMS_H

#include "./EnvelopeQtPtr.h"

CAF_BEGIN_TYPE_ID_BLOCK(groovesprings, first_custom_type_id)

  // typeIds
  CAF_ADD_TYPE_ID(groovesprings, (EnvelopeQtPtr))

  // atoms
  CAF_ADD_ATOM(groovesprings, supervisor_status_a)

  CAF_ADD_ATOM(groovesprings, tc_trig_a)
  CAF_ADD_ATOM(groovesprings, tc_trig_ar)


CAF_END_TYPE_ID_BLOCK(groovesprings)

#endif //MAIN_H
