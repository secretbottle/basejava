package ru.javawebinar.storage;

import ru.javawebinar.storage.strategy.XmlStreamSerializableStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamSerializableStrategy()));
    }
}
