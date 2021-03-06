package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public Integer getSearchKey(String searchKey) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(searchKey)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    public void remove(int index) {
        storage[index] = storage[size - 1];
    }

}
