package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateElement(Object index, Resume resume) {
        storage.replace((String) index, resume);
    }

    @Override
    protected void saveElement(Object index, Resume resume) {
        storage.put((String) index, resume);
    }

    @Override
    protected Resume getElement(Object index) {
        return storage.get(index.toString());
    }

    @Override
    protected void deleteElement(Object index) {
        storage.remove(index.toString());
    }

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object index) {
        return storage.containsKey((String) index);
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
