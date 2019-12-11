package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void saveElement(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(Integer index) {
        storage.remove(index.intValue());
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
    protected boolean isExist(Integer resume) {
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
