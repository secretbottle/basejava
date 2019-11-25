package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    AbstractArrayStorageTest(Storage storage){
        this.storage = storage;
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0,storage.size());
    }

    @Test
    public void update() throws Exception {
        storage.update(storage.get(UUID_2));
        Assert.assertEquals(UUID_2, storage.get(UUID_2).getUuid());
    }

    @Test (expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume());
    }

    @Test
    public void save() throws Exception {
        Assert.assertEquals(UUID_2, storage.get(UUID_2).getUuid());
    }

    @Test(expected = ExistStorageException.class)
    public void saveIsExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID_1),storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        storage.get(UUID_1);
    }

    @Test (expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid46");
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(3,storage.getAll().length);
    }

    @Test
    public void size() throws Exception{
        Assert.assertEquals(3,storage.size());
    }
}