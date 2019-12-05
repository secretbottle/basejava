package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

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
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return (Resume[]) storage.values().toArray();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
