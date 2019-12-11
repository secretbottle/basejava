package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateElement(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }

    @Override
    protected void saveElement(String uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    protected Resume getElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(String uuid) {
        storage.remove(uuid);
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
    protected boolean isExist(String searchKey) {
        return storage.containsKey((String) searchKey);
    }

}
