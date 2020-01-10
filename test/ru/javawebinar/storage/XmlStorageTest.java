package ru.javawebinar.storage;

import ru.javawebinar.storage.strategy.XmlStreamSerializableStrategy;

public class XmlStorageTest extends AbstractStorageTest {
    public XmlStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamSerializableStrategy()));
    }
}
