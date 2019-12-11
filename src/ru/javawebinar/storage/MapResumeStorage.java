package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateElement(Resume searchKey, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void saveElement(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Resume resume) {
        return resume;
    }

    @Override
    protected void deleteElement(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

}
