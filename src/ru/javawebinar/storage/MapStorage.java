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
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object index) {
        return storage.get((String) index);
    }

    @Override
    protected void deleteElement(Object index) {
        storage.remove((String) index);
    }

    @Override
    protected Object getIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            String key = entry.getKey();
            if (key.equals(uuid))
                return key;
        }
        return null;
    }

    @Override
    protected boolean isExist(Object index) {
        return index == null;
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
