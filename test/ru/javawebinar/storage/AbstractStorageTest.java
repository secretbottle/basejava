package ru.javawebinar.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;
    //protected static final File STORAGE_DIR = new File("C:\\Users\\060Adm18\\workspace\\basejava\\storage");
    protected static final File STORAGE_DIR = new File("E:\\workdir\\basejava\\storage");
    //protected static final File STORAGE_DIR = new File("/home/jacj/Documents/workdir/basejava/storage/");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String FULLNAME_1 = "FULLNAME1";
    private static final String FULLNAME_2 = "FULLNAME2";
    private static final String FULLNAME_3 = "FULLNAME3";
    private static final String FULLNAME_4 = "FULLNAME4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.generateResume(UUID_1, FULLNAME_1);
        RESUME_2 = ResumeTestData.generateResume(UUID_2, FULLNAME_2);
        RESUME_3 = ResumeTestData.generateResume(UUID_3, FULLNAME_3);
        RESUME_4 = ResumeTestData.generateResume(UUID_4, FULLNAME_4);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, FULLNAME_4);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_2);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.delete(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(FULLNAME_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> listTest = storage.getAllSorted();
        assertEquals(3, listTest.size());

        Resume[] arrTest = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertEquals(listTest, Arrays.asList(arrTest));
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}