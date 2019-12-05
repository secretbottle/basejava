package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object index = getExistKey(resume.getUuid());
        updateElement(index, resume);
    }

    @Override
    public void save(Resume resume) {
        Object index = getNotExistKey(resume.getUuid());
        saveElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getExistKey(uuid);
        return getElement(index);
    }

    @Override
    public void delete(String uuid) {
        Object index = getExistKey(uuid);
        deleteElement(index);
    }

    private Object getExistKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey))
            throw new NotExistStorageException(uuid);
        return searchKey;
    }

    private Object getNotExistKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey))
            throw new ExistStorageException(uuid);
        return searchKey;
    }

    protected abstract void updateElement(Object index, Resume resume);

    protected abstract void saveElement(Object index, Resume resume);

    protected abstract Resume getElement(Object index);

    protected abstract void deleteElement(Object index);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object index);
}
