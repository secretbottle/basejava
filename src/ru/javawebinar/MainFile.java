package ru.javawebinar;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
        String ROOT = "./src/ru/javawebinar";
        File dir = new File(ROOT);
        outputFileName(Objects.requireNonNull(dir.listFiles(), "Array is null"));
    }

    private static void outputFileName(File[] arr) {
        for (File f : arr) {
            if (f.isDirectory()) {
                System.out.println(f.getPath());
                outputFileName(Objects.requireNonNull(f.listFiles(), "Array is null"));
            }

            if (f.isFile())
                System.out.println(f.getName());
        }
    }
}