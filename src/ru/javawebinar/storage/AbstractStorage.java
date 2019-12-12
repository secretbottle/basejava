package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void updateElement(SK index, Resume resume);

    protected abstract void saveElement(SK index, Resume resume);

    protected abstract Resume getElement(SK index);

    protected abstract void deleteElement(SK index);

    protected abstract SK getSearchKey(String searchKey);

    protected abstract boolean isExist(SK index);

    protected abstract List <Resume> getAsList();

    @Override
    public void update(Resume resume) {
        LOG.info("Resume" + resume);
        SK index = getExistSearchKey(resume.getUuid());
        updateElement(index, resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("save" + resume);
        SK index = getNotExistSearchKey(resume.getUuid());
        saveElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get" + uuid);
        SK index = getExistSearchKey(uuid);
        return getElement(index);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete" + uuid);
        SK index = getExistSearchKey(uuid);
        deleteElement(index);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> sortedList = getAsList();
        Collections.sort(sortedList);
        return sortedList;
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
