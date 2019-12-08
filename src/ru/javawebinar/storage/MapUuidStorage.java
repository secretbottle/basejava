package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateElement(Object uuid, Resume resume) {
        storage.replace((String) uuid, resume);
    }

    @Override
    protected void saveElement(Object uuid, Resume resume) {
        storage.put((String) uuid, resume);
    }

    @Override
    protected Resume getElement(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected void deleteElement(Object uuid) {
        storage.remove((String) uuid);
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
    protected String getSearchKey(String searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

}
