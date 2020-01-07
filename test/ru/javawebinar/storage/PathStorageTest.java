package ru.javawebinar.storage;

import ru.javawebinar.strategy.ObjectStreamSerializableStrategy;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamSerializableStrategy()));
    }
}
