package ru.javawebinar.storage;

import ru.javawebinar.serializator.IOStream;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new IOStream()));
    }
}
