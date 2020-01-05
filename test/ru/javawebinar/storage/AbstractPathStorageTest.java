package ru.javawebinar.storage;

public class AbstractPathStorageTest extends AbstractStorageTest {
    public AbstractPathStorageTest() {
        super(new AbstractPathStorage(STORAGE_DIR.toString(), new ObjectStreamStorage()));
    }
}
