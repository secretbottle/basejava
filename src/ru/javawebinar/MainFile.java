package ru.javawebinar;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
        String ROOT = "./src/ru/javawebinar";
        File dir = new File(ROOT);
        outputFileName(dir.listFiles());
    }

    private static void outputFileName(File[] arr) {
        Objects.requireNonNull(arr, "Array is null");
        for (File f : arr) {
            if (f.isDirectory())
                outputFileName(Objects.requireNonNull(f.listFiles(), "Array is null"));

            if (f.isFile())
                System.out.println(f.getName());
        }
    }
}