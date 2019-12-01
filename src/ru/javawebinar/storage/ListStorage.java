package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage{
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void abstractUpdate(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected int getIndex(String uuid) {
        storage.indexOf();
    }
}
