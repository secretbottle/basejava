package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void saveElement(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
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
    protected boolean isExist(Object resume) {
        return resume != null;
    }

}
