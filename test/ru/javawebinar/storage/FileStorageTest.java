package ru.javawebinar.storage;

import ru.javawebinar.storage.serial.ObjectStreamSerializableStrategy;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializableStrategy()));
    }
}
