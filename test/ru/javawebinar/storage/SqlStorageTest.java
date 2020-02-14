package ru.javawebinar.storage;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                CONFIG.getDbUrl(),
                CONFIG.getDbUser(),
                CONFIG.getDbPassword()
        ));
    }
}
