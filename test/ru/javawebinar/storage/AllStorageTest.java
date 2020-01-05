package ru.javawebinar.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedStorageTest.class,
                ListStorageTest.class,
                MapUuidStorageTest.class,
                MapResumeStorageTest.class,
                AbstractFileStorageTest.class,
                AbstractPathStorage.class
        })
public class AllStorageTest {
}
