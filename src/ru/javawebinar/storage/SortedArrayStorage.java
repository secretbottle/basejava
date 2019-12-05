package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected void add(int index, Resume resume) {
        int addIndex = -index - 1;
        System.arraycopy(storage, addIndex, storage, addIndex + 1, size - addIndex);
        storage[addIndex] = resume;
    }

    @Override
    protected void remove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

}
