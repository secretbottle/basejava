package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializableStorage {
    public void doWrite(OutputStream os, Resume resume) throws IOException;

    public Resume doRead(InputStream is) throws IOException;
}
