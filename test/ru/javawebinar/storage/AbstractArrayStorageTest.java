package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("test Name 1"));
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume("test Name 2"));
    }
}
