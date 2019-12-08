package ru.javawebinar.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String FULLNAME_1 = "uuid 1";
    private static final Resume RESUME_1 = new Resume(FULLNAME_1);
    private static final String FULLNAME_2 = "uuid 2";
    private static final Resume RESUME_2 = new Resume(FULLNAME_2);
    private static final String FULLNAME_3 = "uuid 3";
    private static final Resume RESUME_3 = new Resume(FULLNAME_3);

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(FULLNAME_2);
        storage.update(resume);
        assertGet(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid 404"));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_3);
        assertSize(3);
        assertGet(RESUME_3);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(FULLNAME_1);
        assertSize(1);
        storage.delete(FULLNAME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(FULLNAME_3);
    }
//TODO проверить не надо ли тестировать с учетом примененных изменений
    @Test
    public void getAll() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
    }

    @Test
    public void size() throws Exception {
        assertSize(2);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}