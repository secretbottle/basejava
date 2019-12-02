package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        updateElement(NotExistElement(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index >= 0)
            throw new ExistStorageException(resume.getUuid());

        saveElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        return getElement(NotExistElement(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteElement(NotExistElement(uuid));
    }

    private int NotExistElement(String uuid) {
        int index = getIndex(uuid);
        if (index < 0)
            throw new NotExistStorageException(uuid);
        return index;
    }

    protected abstract void updateElement(int index, Resume resume);

    protected abstract void saveElement(int index, Resume resume);

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(int index);

    protected abstract int getIndex(String uuid);

    public abstract int size();
}
