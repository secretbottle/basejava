package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void saveElement(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void deleteElement(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected Integer getSearchKey(String searchKey) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(searchKey)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
