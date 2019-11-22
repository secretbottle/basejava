package ru.javawebinar.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("ERROR: '" + uuid + "' not exist.", uuid);
    }
}
