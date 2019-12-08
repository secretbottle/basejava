package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateElement(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void saveElement(Object index, Resume resume) {
        if (size < storage.length) {
            add((Integer) index, resume);
            size++;
        } else {
            throw new StorageException("Array overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume getElement(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void deleteElement(Object index) {
        remove((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected List<Resume> getAsList() {
        return Arrays.asList(storage);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void add(int index, Resume resume);

    protected abstract void remove(int index);

}
