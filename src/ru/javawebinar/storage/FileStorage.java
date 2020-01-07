package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.strategy.SerializableStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private SerializableStrategy serializableStrategy;

    protected FileStorage(File directory, SerializableStrategy serializableStrategy) {
        Objects.requireNonNull(directory, " directory must not be null");
        this.serializableStrategy = serializableStrategy;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable or writable");
        }
        this.directory = directory;
    }

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            serializableStrategy.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("IOError at write operation: ", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveElement(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("IOError Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateElement(file, resume);
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return serializableStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IOError at read operation: ", file.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete())
            throw new StorageException("IOError at delete operation: ", file.getName());
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getAsList() {
        File[] fileList = getFileList();
        List<Resume> resumeList = new ArrayList<>();

        for (File file : fileList) {
            resumeList.add(getElement(file));
        }

        return resumeList;
    }

    @Override
    public void clear() {
        File[] fileList = getFileList();
        for (File file : fileList) {
            deleteElement(file);
        }
    }

    @Override
    public int size() {
        return getFileList().length;
    }

    private File[] getFileList() {
        File[] fileList = directory.listFiles();
        if (fileList == null)
            throw new StorageException("IOError while getting listFiles in directory: ", directory.getPath());
        return fileList;
    }
}
