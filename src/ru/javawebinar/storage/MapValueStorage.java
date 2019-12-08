package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapValueStorage extends AbstractMapStorage {
    @Override
    protected Resume getSearchKey(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsValue((Resume) searchKey);
    }

}
