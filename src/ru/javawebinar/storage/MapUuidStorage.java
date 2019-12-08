package ru.javawebinar.storage;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected String getSearchKey(String searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

}
