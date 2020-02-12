package ru.javawebinar.storage;

import ru.javawebinar.SqlStorage;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                CONFIG.getDbUrl(),
                CONFIG.getDbUser(),
                CONFIG.getDbPassword()
        ));
    }
}
