package dev.nateschieber.groovesprings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class GsMusicLibraryScanner {

    public static void main(String[] args) {
        System.out.println("======== GS MUSIC LIBRARY SCANNER");

        String rootDir = System.getProperty("user.dir");
        String musicLibDir = rootDir + "/gs_music_library";


        try (Stream<Path> stream = Files.walk(Paths.get(musicLibDir), 4)) {
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
        } catch(IOException e) {
            System.out.println("File read error.");
        }

    }

    private static boolean fileNameEndsWithValidExtension(String fileName) {
        // TODO
        return true;
    }
}
