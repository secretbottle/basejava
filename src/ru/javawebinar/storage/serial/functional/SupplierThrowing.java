package ru.javawebinar.storage.serial.functional;

import java.io.IOException;

public interface SupplierThrowing<T> {
    T get() throws IOException;
}
