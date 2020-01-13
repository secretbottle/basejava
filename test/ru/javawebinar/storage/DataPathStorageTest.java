package ru.javawebinar.storage;

import ru.javawebinar.storage.strategy.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DataStreamSerializer()));
    }
}
