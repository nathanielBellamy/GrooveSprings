#include <string>
#include <sndfile.h>

class Audio
{
        AUDIO_DATA data;

    public:

        Audio(string file)
        {
            std::cout << "\n audio.cpp has file: " << file;
        }
}