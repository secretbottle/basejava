package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateElement(Object uuid, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void saveElement(Object uuid, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object uuid) {
        return storage.get(((Resume) uuid).getUuid());
    }

    @Override
    protected void deleteElement(Object uuid) {
        storage.remove(((Resume) uuid).getUuid());
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
        //return storage.containsValue((Resume) searchKey);
        return resume != null;
    }

}
