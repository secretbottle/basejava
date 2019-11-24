package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public SortedArrayStorage(){

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void add(Resume resume, int index) {
        int addIndex = -index - 1;
        System.arraycopy(storage, addIndex, storage, addIndex + 1, size - addIndex);
        storage[addIndex] = resume;
    }

    @Override
    protected void remove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

}
