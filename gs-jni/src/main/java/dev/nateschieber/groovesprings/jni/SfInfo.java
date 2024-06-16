package dev.nateschieber.groovesprings.jni;

// Java wrapper for SF_INFO C struct
// http://www.mega-nerd.com/libsndfile/api.html
public record SfInfo(
        int frames,
        int samplerate,
        int channels,
        int format,
        int sections,
        int seekable
) {
}
