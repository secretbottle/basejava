package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Parameter directory is null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable or writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(File file, Resume resume) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IOError at write operation:", file.getName(), e);
        }
    }

    @Override
    protected void saveElement(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IOError at create or write operation:", file.getName(), e);
        }
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return doRead(file);
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
            try {
                resumeList.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IOError at read operation: ", file.getName(), e);
            }
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
        File[] fileList = getFileList();
        return fileList.length;
    }

    private File[] getFileList() {
        File[] fileList = directory.listFiles();
        if (fileList == null)
            throw new StorageException("IOError while getting listFiles in directory: ", directory.getPath());
        return fileList;
    }
}
