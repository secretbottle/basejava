package ru.javawebinar.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: '" + uuid + "' already exist.", uuid);
    }
}
