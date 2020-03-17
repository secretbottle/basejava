package ru.javawebinar.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.Config;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final Config CONFIG = Config.getInstance();
    protected static final File STORAGE_DIR = CONFIG.getStorageDir();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String FULLNAME_1 = "FULLNAME14";
    private static final String FULLNAME_2 = "FULLNAME25";
    private static final String FULLNAME_3 = "FULLNAME31";
    private static final String FULLNAME_4 = "FULLNAME42";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {


        RESUME_1 = ResumeTestData.generateResume(UUID_1, FULLNAME_1);
        RESUME_2 = ResumeTestData.generateResume(UUID_2, FULLNAME_2);
        RESUME_3 = ResumeTestData.generateResume(UUID_3, FULLNAME_3);
        RESUME_4 = ResumeTestData.generateResume(UUID_4, FULLNAME_4);

/*
        RESUME_1 = ResumeTestData.genNullSections(UUID_1, FULLNAME_1);
        RESUME_2 = ResumeTestData.genNullSections(UUID_2, FULLNAME_2);
        RESUME_3 = ResumeTestData.genNullSections(UUID_3, FULLNAME_3);
        RESUME_4 = ResumeTestData.genNullSections(UUID_4, FULLNAME_4);
*/

/*
        RESUME_1 = ResumeTestData.genNullContacts(UUID_1, FULLNAME_1);
        RESUME_2 = ResumeTestData.genNullContacts(UUID_2, FULLNAME_2);
        RESUME_3 = ResumeTestData.genNullContacts(UUID_3, FULLNAME_3);
        RESUME_4 = ResumeTestData.genNullContacts(UUID_4, FULLNAME_4);
*/

/*
        RESUME_1 = new Resume(UUID_1, FULLNAME_1);
        RESUME_1.putContactMap(ContactType.PHONE, "+7999999999");
        RESUME_2 = new Resume(UUID_2, FULLNAME_2);
        RESUME_2.putContactMap(ContactType.SKYPE, "SKYPENAME");
        RESUME_3 = new Resume(UUID_3, FULLNAME_3);
        RESUME_3.putContactMap(ContactType.EMAIL, "email@mail.com");
        RESUME_4 = new Resume(UUID_4, FULLNAME_4);
        RESUME_4.putContactMap(ContactType.HOMEPAGE, "homepage.com");
*/
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_1);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = ResumeTestData.generateResume(UUID_1, "New FULLNAME");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resume = new Resume(UUID_4, "New Name");
        storage.update(resume);
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
        List<Resume> actualList = storage.getAllSorted();
        assertEquals(3, actualList.size());
        List<Resume> expectedList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(expectedList);
        assertEquals(expectedList, actualList);
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