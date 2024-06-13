package dev.nateschieber.groovesprings.workers;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.rest.TrackClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Scanner {

    public static void run(String[] args) {
        List<String> musicLibDirs = musicLibDirs(List.of(args));
        musicLibDirs.forEach(System.out::println);

        musicLibDirs.forEach(Scanner::runScan);
    }

    private static void runScan(String musicLibDir) {
        TrackClient client = new TrackClient();
        try (Stream<Path> stream = Files.walk(Paths.get(musicLibDir), 7)) {
            List<String> files = stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(fileName -> {
                        String name = fileName.getFileName().toString();
                        boolean isNotDsStore = !name.equals(".DS_Store");
                        boolean endsWithValidFileExtension = fileNameEndsWithValidExtension(name);

                        return isNotDsStore
                                && endsWithValidFileExtension;
                    })
                    .map(Path::toString)
                    .toList();

            files.forEach(System.out::println);

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
