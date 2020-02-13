package ru.javawebinar.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: '" + uuid + "' already exist.", uuid);
    }

    public ExistStorageException(String uuid, Exception e) {
        super("ERROR: '" + uuid + "' already exist.", e);
    }
}
