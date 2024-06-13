package dev.nateschieber.groovesprings;

import dev.nateschieber.groovesprings.enums.AudioCodec;
import dev.nateschieber.groovesprings.workers.Scanner;

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

        Scanner.run(new String[0]);
    }

}
