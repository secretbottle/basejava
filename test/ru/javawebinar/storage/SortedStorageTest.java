package ru.javawebinar.storage;

public class SortedStorageTest extends AbstractStorageTest {
    public SortedStorageTest() {
        super(new SortedArrayStorage());
    }
}