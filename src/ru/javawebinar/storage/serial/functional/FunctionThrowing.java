package ru.javawebinar.storage.serial.functional;

import java.io.IOException;

@FunctionalInterface
public interface FunctionThrowing {
    void accept() throws IOException;
}
