package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void saveElement(int index, Resume resume) {
        int addIndex = -index - 1;
        storage.add(addIndex, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(int index) {
        storage.remove(index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchElement = new Resume(uuid);
        return storage.indexOf(searchElement);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
