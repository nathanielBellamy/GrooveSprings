package dev.nateschieber.groovesprings.workers;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.enums.DefaultStrings;
import dev.nateschieber.groovesprings.rest.LocalTrackCreateDto;
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
                        boolean isNotDsStore = !name.equals(".DS_Store");
                        boolean endsWithValidFileExtension = fileNameEndsWithValidExtension(name);

                        return isNotDsStore
                                && endsWithValidFileExtension;
                    })
                    .toList();

            List<LocalTrackCreateDto> localTracks = localTrackCreateDtosFromPaths(paths);
            boolean res = client.bulkCreate(localTracks);

            // TODO:
            // - read metadata into TrackCreateDtos
            // - add path to Track in gs-track-service
            // - alternatively, create LocalTrack on top of Track, similar to PricedTrack
            // - playlist entity in gs-track-service + frontend integration
            // - gs_desktop_work + frontend handle playlist playback
        } catch(IOException e) {
            System.out.println("File read error.");
        }
    }

    private static List<LocalTrackCreateDto> localTrackCreateDtosFromPaths(List<Path> paths) {
         return paths
                 .stream()
                 .map(Scanner::localTrackCreateDtoFromPath)
                 .filter(Objects::nonNull)
                 .toList();
    }

    private static LocalTrackCreateDto localTrackCreateDtoFromPath(Path path) {
        List<String> artistNames = List.of(DefaultStrings.UNKNOWN_ARTIST.getString());
        String albumTitle = DefaultStrings.UNTITLED_ALBUM.getString();
        String trackTitle = DefaultStrings.UNTITLED_TRACK.getString();
        Integer trackNumber = 0;
        Integer trackLength = 0;
        Integer sampleRate = 0;
        Long bitRate = 0l;
        Boolean isVariableBitRate = false;
        Boolean isLoseless = false;
        String audioCodec = "";

        try {
            AudioFile f = AudioFileIO.read(path.toFile());
            Tag tag     = f.getTag();
            AudioHeader header = f.getAudioHeader();
            artistNames = tag.getAll(FieldKey.ARTIST);
            albumTitle  = tag.getFirst(FieldKey.ALBUM);
            trackNumber = parseInt(tag.getFirst(FieldKey.TRACK));
            trackTitle  = tag.getFirst(FieldKey.TITLE);
            trackLength = header.getTrackLength();
            sampleRate  = header.getSampleRateAsNumber();
            bitRate     = header.getBitRateAsNumber();
            isVariableBitRate = header.isVariableBitRate();
            isLoseless = header.isLossless();
            audioCodec = header.getFormat().toUpperCase();
        } catch (Exception e) {
            // TODO: log and report failures
            return null;
        }

        return new LocalTrackCreateDto(
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
                audioCodec
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
