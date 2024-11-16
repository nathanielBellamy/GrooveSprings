//
// Created by ns on 11/16/24.
//

#ifndef ENVELOPE_H
#define ENVELOPE_H

struct Envelope { long ptr; };

template <class Inspector>
bool inspect(Inspector& f, Envelope& x) {
    return f.object(x).fields(f.field("ptr", x.ptr));
}

#endif //ENVELOPE_H
