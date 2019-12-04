package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        updateElement(NotExistElementCheck(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        Object index = ExistElementCheck(resume.getUuid());
        saveElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        return getElement(NotExistElementCheck(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteElement(NotExistElementCheck(uuid));
    }

    private Object NotExistElementCheck(String uuid) {
        Object index = getIndex(uuid);
        if (!isExist(index))
            throw new NotExistStorageException(uuid);
        return index;
    }

    private Object ExistElementCheck(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index))
            throw new ExistStorageException(uuid);
        return index;
    }

    protected abstract void updateElement(Object index, Resume resume);

    protected abstract void saveElement(Object index, Resume resume);

    protected abstract Resume getElement(Object index);

    protected abstract void deleteElement(Object index);

    protected abstract Object getIndex(String uuid);

    protected abstract boolean isExist(Object index);
}
