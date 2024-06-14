#include <string>
#include <unistd.h>
#include <iostream>
#include <sndfile.hh>
#include <portaudio.h>
#include "jni.h"
#include "./audio.h"
#include "./audio_data.h"
#include "./constants.h"
#include "./jni_data.h"

#define PA_SAMPLE_TYPE      paFloat32

typedef float SAMPLE;

Audio::Audio(JNIEnv* env, jstring jFileName, jlong initialFrameId) :
  jniEnv(env)
  , fileName(env->GetStringUTFChars(jFileName, 0))
  , initialFrameId(initialFrameId) {}

void Audio::freeAudioData(AUDIO_DATA *audioData) {
  free(audioData->buffer);
  sf_close(audioData->file);
};

// portaudio callback
// do not allocate/free memory within this method
// as it may be called at system-interrupt level
int Audio::callback(const void *inputBuffer, void *outputBuffer,
                    unsigned long framesPerBuffer,
                    const PaStreamCallbackTimeInfo* timeInfo,
                    PaStreamCallbackFlags statusFlags,
                    void *userData )
{
  SAMPLE *out = (SAMPLE*)outputBuffer;
  unsigned int i;
  (void) inputBuffer;
  (void) timeInfo; /* Prevent unused variable warnings. */
  (void) statusFlags;
  AUDIO_DATA *audioData = (AUDIO_DATA *) userData;

  if( audioData->buffer == NULL )
  {
      for( i=0; i < framesPerBuffer; i++ )
      {
          *out++ = 0;  /* left - silent */
          *out++ = 0;  /* right - silent */
      }
  }
  else if (audioData->index > audioData->sfinfo.frames * audioData->sfinfo.channels + 1)
  {
     return paComplete;
  }
  else if (audioData->index < 0)
  {
    return paComplete;
  }
  // audioData->buffer --> paOut
  else if (audioData->playbackSpeed == -1.0) // reverse
  {
    for (i = 0; i < framesPerBuffer * audioData->sfinfo.channels; i++) {
        *out++ = audioData->buffer[audioData->index - i];
    }

    audioData->index -= framesPerBuffer * audioData->sfinfo.channels;
  }
  else if (audioData->playbackSpeed == 0.5) // half-speed
  {
    int halfFramesPerBuffer = framesPerBuffer / 2; // framesPerBuffer is a power of 2
    for (i = 0; i < halfFramesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index + i];
      *out++ = audioData->buffer[audioData->index + i];
    }

    audioData->index += halfFramesPerBuffer * audioData->sfinfo.channels;
  }
  else if (audioData->playbackSpeed == -0.5) // half-speed reverse
  {
    int halfFramesPerBuffer = framesPerBuffer / 2; // framesPerBuffer is a power of 2
    for (i = 0; i < halfFramesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index - i];
      *out++ = audioData->buffer[audioData->index - i];
    }

    audioData->index -= halfFramesPerBuffer * audioData->sfinfo.channels;
  }
  else if (audioData->playbackSpeed == 2.0) // double speed
  {
    for (i = 0; i < framesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index + (i * 2)];
    }

    audioData->index += (2 * framesPerBuffer) * audioData->sfinfo.channels;
  }
  else if (audioData->playbackSpeed == -2.0) // double speed reverse
  {
    for (i = 0; i < framesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index - (2 * i)];
    }

    audioData->index -= (2 * framesPerBuffer) * audioData->sfinfo.channels;
  }
  else // play
  {
    for (i = 0; i < framesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index + i];
    }

    audioData->index += framesPerBuffer * audioData->sfinfo.channels;
  }

  return paContinue;
};

int Audio::run()
{
  // intialize data needed for audio playback
  sf_count_t index = 0;

  SF_INFO sfinfo;
  // https://svn.ict.usc.edu/svn_vh_public/trunk/lib/vhcl/libsndfile/doc/api.html
  // > When opening a file for read, the format field should be set to zero before calling sf_open().
  sfinfo.format = 0;
  SNDFILE *file;

//  if (! (file = sf_open("gs_music_library/Unknown Artist/Unknown Album/test.mp3", SFM_READ, &sfinfo)))
  if (! (file = sf_open(Audio::fileName, SFM_READ, &sfinfo)))
  {
    printf ("Not able to open input file.\n") ;
    /* Print the error message from libsndfile. */
    puts (sf_strerror (NULL)) ;
    return 1 ;
  };

  // Allocate memory for data
  float *buffer;
  buffer = (float *) malloc(sfinfo.frames * sfinfo.channels * sizeof(float));
  if (!buffer) {
      printf("\nCannot allocate memory");
      return 1;
  }

  // Read the audio data into buffer
  long readcount = sf_read_float(file, buffer, sfinfo.frames * sfinfo.channels);
  if (readcount == 0) {
      printf("\nCannot read file");
      return 1;
  }

  sf_count_t initialFrameId = (sf_count_t) Audio::initialFrameId;
  std::cout << "initialFrameId: " << initialFrameId << "\n";
  AUDIO_DATA audioData(buffer, file, sfinfo, initialFrameId, readcount, 1);

  // init jniData
  JNI_DATA jniData(Audio::jniEnv);

  // Init PA
  PaStreamParameters inputParameters, outputParameters;
  PaStream *stream;
  PaError err;

  err = Pa_Initialize();
  if( err != paNoError ) goto error;

  inputParameters.device = Pa_GetDefaultInputDevice(); /* default input device */
  if (inputParameters.device == paNoDevice) {
      fprintf(stderr,"\nError: No default input device.");
      goto error;
  }
  inputParameters.channelCount = 2;       /* stereo in from file */
  inputParameters.sampleFormat = PA_SAMPLE_TYPE;
  inputParameters.hostApiSpecificStreamInfo = NULL;

  outputParameters.device = Pa_GetDefaultOutputDevice(); /* default output device */
  if (outputParameters.device == paNoDevice) {
      fprintf(stderr,"\nError: No default output device.");
      goto error;
  }
  outputParameters.channelCount = 2;       /* stereo output */
  outputParameters.sampleFormat = PA_SAMPLE_TYPE;
  outputParameters.suggestedLatency = Pa_GetDeviceInfo( outputParameters.device )->defaultLowOutputLatency;
  outputParameters.hostApiSpecificStreamInfo = NULL;

  err = Pa_OpenStream(
            &stream,
            &inputParameters,
            &outputParameters,
            audioData.sfinfo.samplerate,
            AUDIO_BUFFER_FRAMES,
            paNoFlag, /* paClipOn, */
            callback,
            &audioData );
  if( err != paNoError ) goto error;

  err = Pa_StartStream( stream );
  if( err != paNoError ) goto error;

  while( audioData.playState != 0 && audioData.playState != 2 && audioData.index > -1 ) // 0: STOP, 1: PLAY, 2: PAUSE, 3: RW, 4: FF
  {
    // hold thread open until stopped

    // here is our chance to pull data out of the JVM through the jniData obj
    // and
    // make it accessible to our running audio callback through the audioData obj

    audioData.playbackSpeed = jniData.env->CallStaticFloatMethod(
        jniData.gsPlayback,
        jniData.getPlaybackSpeedFloat
    );

    audioData.playState = jniData.env->CallStaticIntMethod(
        jniData.gsPlayback,
        jniData.getPlayStateInt
    );

//    std::cout << "\n =========== \n";
//    std::cout << "\n audioData.playState: " << audioData.playState << "\n";
//    std::cout << "\n audioData.index " << audioData.index << "\n";
//    std::cout << "\n =========== \n";

   Audio::jSetCurrFrameId(&jniData, (int) audioData.index);

  }

  err = Pa_StopStream( stream );
  if( err != paNoError ) goto error;

  err = Pa_CloseStream( stream );
  if( err != paNoError ) goto error;
  Pa_Terminate();
  Audio::freeAudioData(&audioData);
  return 0;

  error:
    Pa_Terminate();
    Audio::freeAudioData(&audioData);
    fprintf( stderr, "\nAn error occurred while using the portaudio stream" );
    fprintf( stderr, "\nError number: %d", err );
    fprintf( stderr, "\nError message: %s", Pa_GetErrorText( err ) );
    return 1;
};

void Audio::jSetCurrFrameId(
    JNI_DATA* jniData,
    int currFrameId
){
   jobject jCurrFrameId = jniData->env->NewObject(
        jniData->jNum,
        jniData->jNumInit,
        currFrameId
   );
   jniData->env->CallVoidMethod(
        jniData->gsPlayback,
        jniData->setCurrFrameId,
        jCurrFrameId
   );
}
