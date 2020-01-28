package ru.javawebinar.storage.serial.functional;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerThrowing<T> {
    void accept(T t) throws IOException;
}
