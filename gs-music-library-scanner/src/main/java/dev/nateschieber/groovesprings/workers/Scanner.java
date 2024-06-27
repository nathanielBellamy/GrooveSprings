package dev.nateschieber.groovesprings.workers;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.DefaultStrings;
import dev.nateschieber.groovesprings.jni.JniMain;
import dev.nateschieber.groovesprings.jni.SfInfo;
import dev.nateschieber.groovesprings.rest.GsDesktopTrackCreateDto;
import dev.nateschieber.groovesprings.rest.TrackClient;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Scanner {

    public static void run(String[] args) {
        List<String> musicLibDirs = musicLibDirs(List.of(args));

        musicLibDirs.forEach(Scanner::runScan);
    }

    private static void runScan(String musicLibDir) {
        TrackClient client = new TrackClient();
        try (Stream<Path> stream = Files.walk(Paths.get(musicLibDir), 7)) {
            List<Path> paths = stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(fileName -> {
                        String name = fileName.getFileName().toString();
                        return fileNameEndsWithValidExtension(name);
                    })
                    .toList();

            List<GsDesktopTrackCreateDto> localTracks = localTrackCreateDtosFromPaths(paths);
            client.bulkCreate(localTracks);

            // TODO:
            // - [x] read metadata into TrackCreateDtos
            // - [x] add path to Track in gs-track-service
            // - [x] alternatively, create LocalTrack on top of Track, similar to PricedTrack
            // - [x] playlist entity in gs-track-service + frontend integration
            // - [ ] gs_desktop_work + frontend handle playlist playback
            // - [ ] clickable progress bar
        } catch(IOException e) {
            System.out.println("File read error.");
        }
    }

    private static List<GsDesktopTrackCreateDto> localTrackCreateDtosFromPaths(List<Path> paths) {
         return paths
                 .stream()
                 .map(Scanner::localTrackCreateDtoFromPath)
                 .filter(Objects::nonNull)
                 .toList();
    }

    private static GsDesktopTrackCreateDto localTrackCreateDtoFromPath(Path path) {
        List<String> artistNames; // List.of(DefaultStrings.UNKNOWN_ARTIST.getString());
        String albumTitle; // DefaultStrings.UNTITLED_ALBUM.getString();
        String trackTitle; // DefaultStrings.UNTITLED_TRACK.getString();
        Integer trackNumber; // 0;
        Integer trackLength; // 0;
        Integer sampleRate; // 0;
        Long bitRate; // 0l;
        Boolean isVariableBitRate; // false;
        Boolean isLoseless; // false;
        AudioCodec audioCodec; // AudioCodec.UNRECOGNIZED;
        String releaseDate; // "";
        int sf_frames; // 0;
        int sf_samplerate; // 0;
        int sf_channels; // 0;
        int sf_format; // 0;
        int sf_sections; // 0;
        int sf_seekable; // 0;

        try {
            AudioFile f = AudioFileIO.read(path.toFile());
            Tag tag     = f.getTag();
            AudioHeader header = f.getAudioHeader();
            artistNames = tag.getAll(FieldKey.ARTIST);
            albumTitle  = tag.getFirst(FieldKey.ALBUM);
            try {
                trackNumber = parseInt(tag.getFirst(FieldKey.TRACK));
            } catch (NumberFormatException e) {
                trackNumber = 0;
            }
            trackTitle  = tag.getFirst(FieldKey.TITLE);
            trackLength = header.getTrackLength();
            sampleRate  = header.getSampleRateAsNumber();
            bitRate     = header.getBitRateAsNumber();
            isVariableBitRate = header.isVariableBitRate();
            isLoseless = header.isLossless();
            audioCodec = AudioCodec.fromStringAndBitRate(
                    header.getFormat().toUpperCase(),
                    bitRate,
                    isVariableBitRate
            );
            releaseDate = tag.getFirst(FieldKey.YEAR);

            if (artistNames.isEmpty()) {
                artistNames = List.of(DefaultStrings.UNKNOWN_ARTIST.getString());
            }
            if (albumTitle == null || albumTitle.isBlank()) {
                albumTitle = DefaultStrings.UNKNOWN_ALBUM.getString();
            }
            if (trackTitle == null || trackTitle.isEmpty()) {
                // if no title is found in meta-data, use filename as track title
                String[] fileSplit = path.getFileName().toString().split("\\.");
                trackTitle = fileSplit[0];
            }

            SfInfo sfInfo = JniMain.readSfInfo(path.toString());

            sf_frames = sfInfo.frames();
            sf_samplerate = sfInfo.samplerate();
            sf_channels = sfInfo.channels();
            sf_format = sfInfo.format();
            sf_sections = sfInfo.sections();
            sf_seekable = sfInfo.seekable();

        } catch (Exception e) {
            // TODO: log and report failures
            return null;
        }

        return new GsDesktopTrackCreateDto(
                path,
                artistNames,
                albumTitle,
                trackTitle,
                trackNumber,
                trackLength,
                sampleRate,
                bitRate,
                isVariableBitRate,
                isLoseless,
                audioCodec,
                releaseDate,
                sf_frames,
                sf_samplerate,
                sf_channels,
                sf_format,
                sf_sections,
                sf_seekable
        );
    }

    private static boolean fileNameEndsWithValidExtension(String fileName) {
        boolean res = false;
        for (AudioCodec codec : AudioCodec.values()) {
            if (fileName.endsWith(codec.getFileExtension())) {
                res = true;
                break;
            }
        }
        return res;
    }

    private static List<String> musicLibDirs(List<String> dirsIn) {
        List<String> dirs = new ArrayList<>(dirsIn);
        String homeDir = System.getProperty("user.home");
        String defaultMusicLibDir = homeDir + "/GrooveSprings_MusicLibrary";

        dirs.add(defaultMusicLibDir);

        return dirs;
    }
}
