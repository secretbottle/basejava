package ru.javawebinar.storage;

import ru.javawebinar.serializator.IOStream;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new IOStream()));
    }
}
