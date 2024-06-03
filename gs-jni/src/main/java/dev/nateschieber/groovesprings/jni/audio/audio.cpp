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

Audio::Audio(JNIEnv* env, jstring jFileName) :
  jniData(env)
  , fileName(env->GetStringUTFChars(jFileName, 0)) {}

void Audio::freeAudioData(AUDIO_DATA *audioData) {
  free(audioData->buffer);
  sf_close(audioData->file);
};

int Audio::init_pa(AUDIO_DATA *audioData)
{
  audioData->index = 0;

  // https://svn.ict.usc.edu/svn_vh_public/trunk/lib/vhcl/libsndfile/doc/api.html
  // > When opening a file for read, the format field should be set to zero before calling sf_open().
  audioData->sfinfo.format = 0;

  if (! (audioData->file = sf_open("gs_music_library/Unknown Artist/Unknown Album/test.mp3", SFM_READ, &audioData->sfinfo)))
  {
		printf ("Not able to open input file.\n") ;
		/* Print the error message from libsndfile. */
		puts (sf_strerror (NULL)) ;
    return 1 ;
  };

  // Allocate memory for data
  audioData->buffer = (float *) malloc(audioData->sfinfo.frames * audioData->sfinfo.channels * sizeof(float));
  if (!audioData->buffer) {
      printf("\nCannot allocate memory");
      return 1;
  }

  // Read the audio data into buffer
  long readcount = sf_read_float(audioData->file, audioData->buffer, audioData->sfinfo.frames * audioData->sfinfo.channels);
  if (readcount == 0) {
      printf("\nCannot read file");
      return 1;
  }

  return 0;
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

//  // TODO: replace stopped with playState, add pause functionality
//  bool stopped;
//  stopped = audioData->jniData->env->CallStaticBooleanMethod(
//        audioData->jniData->gsPlayback,
//        audioData->jniData->getStopped
//  );

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
    // audioData->index = 0;
    // return paComplete;
    return 1;
  }
//  else if (stopped) {
//    return 1;
//  }
  else
  {
    // audioData->buffer --> paOut
    for (i = 0; i < framesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index + i];
    }

    audioData->index += framesPerBuffer * audioData->sfinfo.channels;
  }

  if (audioData->index % 1000 == 0)
  {
// TODO: debug
//       audioData->jCurrFrameId = audioData->jniData.env->NewObject(
//            audioData->jniData.jNum,
//            audioData->jniData.jNumInit,
//            (int) audioData->index
//       );
//       audioData->jniData.env->CallVoidMethod(
//            audioData->jniData.gsPlayback,
//            audioData->jniData.setCurrFrameId,
//            audioData->jCurrFrameId
//       );
  };

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

  if (! (file = sf_open("gs_music_library/Unknown Artist/Unknown Album/test.mp3", SFM_READ, &sfinfo)))
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

  AUDIO_DATA audioData(buffer, file, sfinfo, index, readcount, Audio::jniData);

//   jobject jCurrFrameId = audioData.jniData.env->NewObject(
//     audioData.jniData.jNum,
//     audioData.jniData.jNumInit,
//     555512341234
//   );
//   audioData.jniData.env->CallVoidMethod(
//     audioData.jniData.gsPlayback,
//     audioData.jniData.setCurrFrameId,
//     jCurrFrameId
//   );

  PaStreamParameters inputParameters, outputParameters;
  PaStream *stream;
  PaError err;

  err = Pa_Initialize();
  if( err != paNoError ) goto error;

  std::cout << "\n Audio::run::4";
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

  bool stopped;
  stopped = false;
  while( !stopped == true )
  {
    // hold thread open until stopped
    stopped = Audio::jniData.env->CallStaticBooleanMethod(
        Audio::jniData.gsPlayback,
        Audio::jniData.getStopped
    );
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
