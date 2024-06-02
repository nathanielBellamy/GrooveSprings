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
  env(env)
  , gsPlayback(env->FindClass("dev/nateschieber/groovesprings/actors/GsPlaybackThread"))
  , setCurrFrameId(env->GetStaticMethodID (gsPlayback, "setCurrFrameId", "(Ljava/lang/Long;)V"))
  , getStopped(env->GetStaticMethodID (gsPlayback, "getStopped", "()Z"))
  , jNum(env->FindClass("java/lang/Long"))
  , jNumInit(env->GetMethodID(jNum, "<init>", "(J)V"))
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

//  if (audioData->index % 1000 == 0)
//  {
//    Audio::jSetCurrFrameId(audioData->jniData, audioData->index);
//  };

  return paContinue;
};

int Audio::run()
{
  AUDIO_DATA audioData;
  std::cout << "\n Audio::run::1";

//   jobject jCurrFrameId = Audio::env->NewObject(
//     Audio::jNum,
//     Audio::jNumInit,
//     8888888
//   );
//   Audio::env->CallVoidMethod(
//     Audio::gsPlayback,
//     Audio::setCurrFrameId,
//     jCurrFrameId
//   );

  std::cout << "\n Audio::run::2";
  if ( Audio::init_pa(&audioData) != 0)
  {
//    goto error;
  };

  std::cout << "\n Audio::run::3";
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

  std::cout << "\n Audio::run::5";

  outputParameters.device = Pa_GetDefaultOutputDevice(); /* default output device */
  if (outputParameters.device == paNoDevice) {
      fprintf(stderr,"\nError: No default output device.");
      goto error;
  }
  outputParameters.channelCount = 2;       /* stereo output */
  outputParameters.sampleFormat = PA_SAMPLE_TYPE;
  outputParameters.suggestedLatency = Pa_GetDeviceInfo( outputParameters.device )->defaultLowOutputLatency;
  outputParameters.hostApiSpecificStreamInfo = NULL;

  std::cout << "\n Audio::run::6";
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

  std::cout << "\n Audio::run::7";
  bool stopped;
  stopped = false;
//  while (true) {};
// TODO:
  while( !stopped == true )
  {
    std::cout << "\n Audio::Run::7.1:: " << stopped << "   <--";
    // hold thread open until stopped
    stopped = Audio::env->CallStaticBooleanMethod(
        Audio::gsPlayback,
        Audio::getStopped
    );
  }

  err = Pa_StopStream( stream );
  if( err != paNoError ) goto error;

  err = Pa_CloseStream( stream );
  if( err != paNoError ) goto error;
  Pa_Terminate();
  Audio::freeAudioData(&audioData);
  std::cout << "\n Audio::run::8";
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
