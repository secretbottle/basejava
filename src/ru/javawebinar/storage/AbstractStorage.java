package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract void updateElement(SK index, Resume resume);

    protected abstract void saveElement(SK index, Resume resume);

    protected abstract Resume getElement(SK index);

    protected abstract void deleteElement(SK index);

    protected abstract SK getSearchKey(String searchKey);

    protected abstract boolean isExist(SK index);

    protected abstract List <Resume> getAsList();

    @Override
    public void update(Resume resume) {
        SK index = getExistSearchKey(resume.getUuid());
        updateElement(index, resume);
    }

    @Override
    public void save(Resume resume) {
        SK index = getNotExistSearchKey(resume.getUuid());
        saveElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        SK index = getExistSearchKey(uuid);
        return getElement(index);
    }

    @Override
    public void delete(String uuid) {
        SK index = getExistSearchKey(uuid);
        deleteElement(index);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getAsList();
        Collections.sort(sortedList);
        return sortedList;
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey))
            throw new NotExistStorageException(uuid);
        return searchKey;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey))
            throw new ExistStorageException(uuid);
        return searchKey;
    }

}
