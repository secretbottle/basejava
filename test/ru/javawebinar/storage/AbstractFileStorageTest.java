package ru.javawebinar.storage;

public class AbstractFileStorageTest extends AbstractStorageTest {
    public AbstractFileStorageTest() {
        super(new AbstractFileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
