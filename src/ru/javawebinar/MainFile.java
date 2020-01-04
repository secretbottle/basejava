package ru.javawebinar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainFile {

    public static void main(String[] args) {
        String ROOT = "./src/ru/javawebinar";
        //File dir = new File(ROOT);
        //printFileList(dir.listFiles(), "");

        Path path = Paths.get(ROOT);
        printPaths(path, "");
    }

    private static void printFileList(File[] arr, String indent) {
        Objects.requireNonNull(arr, "Array is null");
        for (File f : arr) {
            if (f.isDirectory()) {
                System.out.println(f.getName());
                printFileList(Objects.requireNonNull(f.listFiles(), "Array is null"), "\t");
            }

            if (f.isFile()) {
                System.out.println(indent + f.getName());
            }
        }
    }

    private static void printPaths(Path path, String indent) {
        try {
            System.out.println(indent + path.getFileName());

            List<String> resultDirectories = Files.list(path).filter(Files::isDirectory)
                    .map(Path::toString).collect(Collectors.toList());

            if (resultDirectories.size() != 0) {
                resultDirectories.forEach(x -> printPaths(Paths.get(x), "\t"));
            }

            List<String> resultFiles = Files.list(path).filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());
            resultFiles.forEach(x -> System.out.println(indent + "\t" + x));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}