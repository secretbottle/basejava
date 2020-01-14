package ru.javawebinar.storage;

import ru.javawebinar.storage.serial.JsonStreamSerializableStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new JsonStreamSerializableStrategy()));
    }
}
