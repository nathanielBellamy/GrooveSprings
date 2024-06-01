#include <string>
#include <unistd.h>
#include <iostream>
#include <sndfile.hh>
#include <portaudio.h>
#include "./audio.h"
#include "./audio_data.h"
#include "./constants.h"

#define PA_SAMPLE_TYPE      paFloat32

void Audio::Audio(char const* file)
{
    this->file = file;
    std::cout << "Enter Audio::Audio";
    char cwd[PATH_MAX];
    if (getcwd(cwd, sizeof(cwd)) != NULL) {
        printf("Current working dir: %s\n", cwd);
    } else {
        perror("getcwd() error");
    }
    std::cout << "\n audio.cpp has file: " << file;
};

void Audio::freeAudioData(AUDIO_DATA *audioData) {
  // printf("\nCleaning up resources...");

  free(audioData->buffer);
  sf_close(audioData->file);

  // printf("\nDone.");
};

int Audio::init_pa(AUDIO_DATA *audioData)
{
  audioData->index = 0;

  // https://svn.ict.usc.edu/svn_vh_public/trunk/lib/vhcl/libsndfile/doc/api.html
  // > When opening a file for read, the format field should be set to zero before calling sf_open().
  audioData->sfinfo.format = 0;

  if (! (audioData->file = sf_open(file, SFM_READ, &audioData->sfinfo)))
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
static int Audio::callback(const void *inputBuffer, void *outputBuffer,
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
    // audioData->index = 0;
    // return paComplete;
    return 1;
  }
  else
  {
    // audioData->buffer --> paOut
    for (i = 0; i < framesPerBuffer * audioData->sfinfo.channels; i++) {
      *out++ = audioData->buffer[audioData->index + i];
    }
  }

  return paContinue;
};

int Audio::run(/* TODO: Pass in args */)
{
  AUDIO_DATA audioData;
  if ( init_pa(&audioData) != 0)
  {
    freeAudioData(&audioData);
    return 1;
  };

//  AUDIO_DATA *audioData = (AUDIO_DATA*)audioData_;
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

  char c;
  while( false /* TODO: !stopped */) {}
  err = Pa_StopStream( stream );
  if( err != paNoError ) goto error;

  err = Pa_CloseStream( stream );
  if( err != paNoError ) goto error;
  Pa_Terminate();
  return 0;

  error:
    Pa_Terminate();
    fprintf( stderr, "\nAn error occurred while using the portaudio stream" );
    fprintf( stderr, "\nError number: %d", err );
    fprintf( stderr, "\nError message: %s", Pa_GetErrorText( err ) );
    return 1;
};