package ru.javawebinar.exception;

import java.sql.SQLException;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: '" + uuid + "' already exist.", uuid);
    }

    public ExistStorageException(String uuid, Exception e) {
        super("ERROR: '" + uuid + "' already exist.", e);
    }

    public ExistStorageException(SQLException e) {
        super("ERROR: uuid is already exist.", e);
    }
}
